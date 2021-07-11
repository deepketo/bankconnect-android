package `in`.finbox.bankconnectdemo

import `in`.finbox.bankconnect.BankActivity
import `in`.finbox.bankconnect.FinBoxBankConnect
import `in`.finbox.bankconnect.payloads.FinboxOnSuccessPayload
import `in`.finbox.bankconnect.utils.FinboxBankConstants
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        // Log cat tag
        private val TAG = MainActivity::class.java.simpleName
    }

    /**
     * Activity Result
     */
    private val result = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        parseActivityResult(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Bank Connect sdk and pass linkId (mandatory)
        buildBankConnect()

        // Start Bank Connect Screen
        startBankActivity()
    }

    private fun buildBankConnect() {
        val document = getStartEndDate()
        FinBoxBankConnect.Builder(applicationContext)
            .apiKey("API_KEY_HERE")
            .linkId(UUID.randomUUID().toString())
            .fromDate(document.startDate)
            .toDate(document.endDate)
            .build()
    }

    private fun startBankActivity() {
        result.launch(Intent(this, BankActivity::class.java))
    }

    /**
     * Parse intent data
     *
     * @param result Parse [ActivityResult]
     */
    fun parseActivityResult(result: ActivityResult?) {
        if (result?.resultCode == Activity.RESULT_OK) {
            // Result is success
            val extras = result.data?.extras
            // Show Error or Parse Success Payload
            extras?.let { successPayload(it) } ?: kotlin.run {
                showError("Failed to Get the Result")
            }
        } else {
            // Result Failed
            showError("Failed to Get the Result")
        }
    }

    /**
     * Read Success payload
     *
     * @param extras Bundle extras
     */
    private fun successPayload(extras: Bundle) {
        // Read the success Payload
        val payload = extras.getParcelable<FinboxOnSuccessPayload>(
            FinboxBankConstants.BUNDLE_EXTRA_SUCCESS_PAYLOAD
        )
        Log.d(TAG, "Entity Id: ${payload?.entityId}")
        Log.d(TAG, "Link Id: ${payload?.linkId}")
        when {
            payload == null -> {
                showError("Failed to Receive Payload")
            }
            payload.entityId.isNullOrBlank() -> {
                // File Didn't Upload
                showError("Failed to Upload Document")
            }
            else -> {
                showSuccess()
            }
        }
    }

    /**
     * Show success snack message
     */
    private fun showSuccess() {
        Snackbar.make(main, "Completed Successfully", Snackbar.LENGTH_LONG).show()
    }

    /**
     * Show error snack message
     */
    private fun showError(error: String) {
        Snackbar.make(main, error, Snackbar.LENGTH_LONG).show()
    }
}
