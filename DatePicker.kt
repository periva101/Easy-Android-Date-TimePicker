package numny.numnyRefactoring.app.subui.datepicker

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.DialogInterface
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*


internal class SetDatePicker(private val editText: EditText, val context: Context) :OnTimeSetListener, DatePickerDialog.OnDateSetListener,  View.OnTouchListener {
    private val myCalendar: Calendar
    private val fmtTimeOnly = SimpleDateFormat("HH:mm:ss", Locale.US)

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        editText.setText("")

        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdformat = SimpleDateFormat(myFormat, Locale.US)
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      editText.setText(sdformat.format(myCalendar.time))
    }


    init {
        editText.keyListener = null
        editText.setOnTouchListener(this)
        myCalendar = Calendar.getInstance()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {


        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = hourOfDay
        cal[Calendar.MINUTE] = minute
        cal[Calendar.SECOND] = 0
        val date = cal.time
        val time = fmtTimeOnly.format(date)
        editText.setText(StringBuilder(editText.text).append(" ").append(time))

    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_UP){
          val timePicker=  TimePickerDialog(context,this,0,0,true)
            timePicker.show()

           val datePickerDialog: DatePickerDialog =  DatePickerDialog(context, this, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH])
            datePickerDialog.datePicker.maxDate =  Date().time


            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,context. getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    timePicker.cancel()
                }
            })

            datePickerDialog.show()
        }

       return true
    }
}