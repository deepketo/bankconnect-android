package `in`.finbox.bankconnectdemo

import `in`.finbox.bankconnect.model.FinBoxBankConnect
import `in`.finbox.bankconnect.payloads.FinboxResult
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* Initialize bankconnect sdk and pass linkId (mandatory) */
        FinBoxBankConnect.Builder(applicationContext, bankConnect)
                .linkId(UUID.randomUUID().toString())
                .build()

        /* set listeners to capture callbacks given by the SDK */
        bankConnect.getPayloadLiveData().observe(this, Observer {
            when (it) {
                is FinboxResult.OnExit -> {
                    Log.i("Sanoop", "On Exit -> ${it.exitPayload}")
                }
//                is FinboxResult.OnSuccess -> {
//                    Log.i("Sanoop", "On Success -> ${it.successPayload}")
//                }
                is FinboxResult.OnError -> {
                    Log.i("Sanoop", "On Error -> ${it.errorPayload}")
                }
            }
        })
    }
}
