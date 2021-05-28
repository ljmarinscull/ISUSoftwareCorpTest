package com.project.acmetest.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.project.acmetest.BR
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class TicketObject(
    var _id: Int = -1,
    var _ticketName: String = "",
    var _customerFullname: String = "",
    var _customerPhone: String = "",
    var _address: String = "",
    var _notes: String = "",
    var month: Int = LocalDate.now().monthValue,
    var year: Int = LocalDate.now().year,
    var date: LocalDate = LocalDate.now()
): BaseObservable(), Parcelable {

    var ticketName: String
        @Bindable get() = _ticketName
        set(value) {
            _ticketName = value
            notifyPropertyChanged(BR.ticketName)
        }

    var customerFullname: String
        @Bindable get() = _customerFullname
        set(value) {
            _customerFullname = value
            notifyPropertyChanged(BR.customerFullname)
        }

    var customerPhone: String
        @Bindable get() = _customerPhone
        set(value) {
            _customerPhone = value
            notifyPropertyChanged(BR.customerPhone)
        }

    var notes: String
        @Bindable get() = _notes
        set(value) {
            _notes = value
            notifyPropertyChanged(BR.notes)
        }

    var address: String
        @Bindable get() = _address
        set(value) {
            _address = value
            notifyPropertyChanged(BR.address)
        }

    val isAllFilled: Boolean
        @Bindable(value = ["ticketName", "customerFullname", "customerPhone", "address"])
        get() {
            if(ticketName.isNotBlank() && ticketName.trim().length >= 4)
                if(customerFullname.isNotBlank() && customerFullname.trim().length >= 4)
                    if(customerPhone.isNotBlank() && customerPhone.trim().length == 10)
                        if(address.isNotBlank() && address.trim().length >= 4 )
                            return true
            return false
        }

}
