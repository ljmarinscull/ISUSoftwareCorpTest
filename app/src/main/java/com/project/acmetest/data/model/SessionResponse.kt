package com.project.acmetest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SessionResponse(val accountId: String = "1", val name: String = "Admin"): Parcelable
