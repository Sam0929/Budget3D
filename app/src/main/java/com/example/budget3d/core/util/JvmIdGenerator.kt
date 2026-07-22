package com.example.budget3d.core.util

import com.example.budget3d.core.domain.util.IdGenerator
import java.util.UUID
import javax.inject.Inject

class JvmIdGenerator @Inject constructor() : IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}