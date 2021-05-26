package com.project.acmetest.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.project.acmetest.BR
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class TicketObject(
    var _id: Int = -1,
    var _clientName: String = "",
    var _address: String = "",
    var month: Int = LocalDate.now().monthValue,
    var year: Int = LocalDate.now().year,
    var date: LocalDate = LocalDate.now()
): BaseObservable(), Parcelable {

    var clientName: String
        @Bindable get() = _clientName
        set(value) {
            _clientName = value
            notifyPropertyChanged(BR.clientName)
        }

    var address: String
        @Bindable get() = _address
        set(value) {
            _address = value
            notifyPropertyChanged(BR.address)
        }

    val isAllFilled: Boolean
        @Bindable(value = ["clientName", "address"])
        get() {
            if(clientName.isNotBlank() && clientName.length >= 4)
                if(address.isNotBlank() && address.length >= 4 )
                    return true
            return false
        }

}
