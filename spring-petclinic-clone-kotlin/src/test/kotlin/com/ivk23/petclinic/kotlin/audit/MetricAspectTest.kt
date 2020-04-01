package com.ivk23.petclinic.kotlin.audit

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class MetricAspectTest {

    @Mock
    private lateinit var pjp: ProceedingJoinPoint

    @Mock
    private lateinit var signature: Signature

    private val classUnderTest = MetricAspect()

    @BeforeEach
    fun setUp() {
        `when`(pjp.signature).thenReturn(signature)
        `when`(signature.declaringTypeName).thenReturn("a.b.c.Class")
        `when`(signature.name).thenReturn("method")
    }

    @AfterEach
    fun after() {
        verify(pjp, times(2)).signature
        verify(signature).declaringTypeName
        verify(signature).name
    }

    @Test
    fun executionTimeAdvice() {
        `when`(pjp.proceed()).thenReturn("result")

        val res = classUnderTest.executionTimeAdvice(pjp)
        assertEquals("result", res)

        verify(pjp).proceed()
    }

    @Test
    fun executionTimeAdviceException() {
        doThrow(ResourceNotFoundException::class.java).`when`(pjp).proceed()
        assertThrows<ResourceNotFoundException> { classUnderTest.executionTimeAdvice(pjp) }
    }
}