package com.ivk23.petclinic.kotlin.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Profile

@Profile("metrics")
@EnableAspectJAutoProxy
@Configuration
class MetricsConfig {}