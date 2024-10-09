package com.example.transactionservice.service.impl;

import com.example.transactionservice.mapper.impl.LimitMapperImpl;
import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.repository.LimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LimitServiceImplTest {

    @Mock
    private LimitRepository limitRepository;

    @Mock
    private LimitMapperImpl limitMapper;

    @InjectMocks
    private LimitServiceImpl limitService;

    private ExpenseCategory expenseCategory;
    private Long accountId;
    private BigDecimal sum;
    private Currency currency;
    private Limit limit;

    @BeforeEach
    void setUp() {
        expenseCategory = ExpenseCategory.GOODS;
        accountId = 1L;
        sum = BigDecimal.valueOf(100.00);
        currency = Currency.USD;
        limit = new Limit();
        limit.setId(1L);
        limit.setExpenseCategory(expenseCategory);
        limit.setAccountId(accountId);
        limit.setBalance(BigDecimal.valueOf(200.00));
        limit.setCurrency(currency);
    }

    @Nested
    @DisplayName("Tests for isLimitExceeded method")
    class IsLimitExceededTests {

        @Test
        @DisplayName("Should return true when sum exceeds balance")
        void testIsLimitExceeded_True() {
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.of(limit));

            boolean result = limitService.isLimitExceeded(expenseCategory, accountId, BigDecimal.valueOf(250));

            assertTrue(result);
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
        }

        @Test
        @DisplayName("Should return false when sum does not exceed balance")
        void testIsLimitExceeded_False() {
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.of(limit));

            boolean result = limitService.isLimitExceeded(expenseCategory, accountId, BigDecimal.valueOf(150));

            assertFalse(result);
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when no limit is found")
        void testIsLimitExceeded_NoLimitFound() {
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    limitService.isLimitExceeded(expenseCategory, accountId, BigDecimal.valueOf(100))
            );

            assertEquals("No limits found for the specified parameters.", exception.getMessage());
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
        }
    }

    @Nested
    @DisplayName("Tests for getLastLimit method")
    class GetLastLimitTests {

        @Test
        @DisplayName("Should return the last limit when present")
        void testGetLastLimit_Present() {
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.of(limit));

            Limit result = limitService.getLastLimit(expenseCategory, accountId);

            assertNotNull(result);
            assertEquals(limit, result);
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when no limit is found")
        void testGetLastLimit_NotPresent() {
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    limitService.getLastLimit(expenseCategory, accountId)
            );

            assertEquals("No limits found for the specified parameters.", exception.getMessage());
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
        }
    }

    @Nested
    @DisplayName("Tests for save(LimitRequest) method")
    class SaveWithLimitRequestTests {

        @Test
        @DisplayName("Should save the limit entity successfully")
        void testSave_LimitRequest_Success() {
            LimitRequest limitRequest = new LimitRequest(1L,BigDecimal.TEN,Currency.USD,ExpenseCategory.SERVICES);
            Limit limitEntity = new Limit();
            when(limitMapper.toEntity(limitRequest)).thenReturn(limitEntity);
            when(limitRepository.save(limitEntity)).thenReturn(limitEntity);

            // Act
            limitService.save(limitRequest);

            // Assert
            verify(limitMapper, times(1)).toEntity(limitRequest);
            verify(limitRepository, times(1)).save(limitEntity);
        }
    }

    @Nested
    @DisplayName("Tests for save(Long, BigDecimal, Currency, ExpenseCategory) method")
    class SaveWithParametersTests {

        @Test
        @DisplayName("Should save the limit with given parameters successfully")
        void testSave_WithParameters_Success() {
            Limit limitEntity = new Limit();
            when(limitMapper.toEntity(accountId, sum, currency, expenseCategory)).thenReturn(limitEntity);
            when(limitRepository.save(limitEntity)).thenReturn(limitEntity);

            Limit result = limitService.save(accountId, sum, currency, expenseCategory);

            assertNotNull(result);
            assertEquals(limitEntity, result);
            verify(limitMapper, times(1)).toEntity(accountId, sum, currency, expenseCategory);
            verify(limitRepository, times(1)).save(limitEntity);
        }

        @Test
        @DisplayName("Should handle mapper returning null")
        void testSave_WithParameters_MapperReturnsNull() {
            when(limitMapper.toEntity(accountId, sum, currency, expenseCategory)).thenReturn(null);

            Limit result = limitService.save(accountId, sum, currency, expenseCategory);

            assertNull(result);
            verify(limitMapper, times(1)).toEntity(accountId, sum, currency, expenseCategory);
            verify(limitRepository, times(1)).save(null);
        }
    }

    @Nested
    @DisplayName("Tests for reductLimit method")
    class ReductLimitTests {

        @Test
        @DisplayName("Should reduct the limit successfully")
        void testReductLimit_Success() {
            BigDecimal reductSum = BigDecimal.valueOf(50.00);
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.of(limit));
            when(limitRepository.save(any(Limit.class))).thenReturn(limit);

            limitService.reductLimit(accountId, reductSum, currency, expenseCategory);

            ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
            verify(limitRepository, times(1)).save(limitCaptor.capture());

            Limit savedLimit = limitCaptor.getValue();
            assertEquals(BigDecimal.valueOf(150.00), savedLimit.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when no limit is found")
        void testReductLimit_NoLimitFound() {
            BigDecimal reductSum = BigDecimal.valueOf(50.00);
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    limitService.reductLimit(accountId, reductSum, currency, expenseCategory)
            );

            assertEquals("No limits found for the specified parameters.", exception.getMessage());
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
            verify(limitRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should handle subtraction leading to negative balance")
        void testReductLimit_NegativeBalance() {
            BigDecimal reductSum = BigDecimal.valueOf(250.00); // More than current balance
            when(limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId))
                    .thenReturn(Optional.of(limit));

            limitService.reductLimit(accountId, reductSum, currency, expenseCategory);

            ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
            verify(limitRepository, times(1)).findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);
            verify(limitRepository, times(1)).save(limitCaptor.capture());

            Limit savedLimit = limitCaptor.getValue();
            assertEquals(BigDecimal.valueOf(-50.00), savedLimit.getBalance());
        }
    }

    @Nested
    @DisplayName("Tests for getAllLimits method")
    class GetAllLimitsTests {

        @Test
        @DisplayName("Should return all limits successfully")
        void testGetAllLimits_Success() {
            Limit limit2 = new Limit();
            limit2.setId(2L);
            limit2.setExpenseCategory(ExpenseCategory.GOODS);
            limit2.setAccountId(2L);
            limit2.setBalance(BigDecimal.valueOf(300.00));
            limit2.setCurrency(Currency.RUB);

            List<Limit> limits = Arrays.asList(limit, limit2);
            when(limitRepository.findAll()).thenReturn(limits);

            List<Limit> result = limitService.getAllLimits();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.contains(limit));
            assertTrue(result.contains(limit2));
            verify(limitRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no limits are present")
        void testGetAllLimits_Empty() {
            when(limitRepository.findAll()).thenReturn(List.of());

            List<Limit> result = limitService.getAllLimits();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(limitRepository, times(1)).findAll();
        }
    }
}
