# Sample Project for BankConnect Android Client SDK 

## Steps to start

In the `AndroidManifest.xml` file add the apiKey in the `meta-tag` given by FinBox team. And you are ready to use the sample app.

In `MainActivity.kt` you can find the implementation of the SDK. Add `FinboxBankConnectView` to the layout file and use the reference to initialize the sdk.
`bankconnect` is the reference to the view.

```kotlin
    FinBoxBankConnect.Builder(applicationContext, bankConnect)
        .linkId(UUID.randomUUID().toString())
        .build()
```

## Live Data

`bankConnect.getPayloadLiveData()` holds the livedata that is updated for every event. In order to listen for callbacks, observe the livedata

```kotlin
    bankConnect.getPayloadLiveData().observe(this, Observer {
            when (it) {
                is FinboxResult.OnExit -> {
                    Log.i("Sanoop", "On Exit -> ${it.exitPayload}")
                }
                is FinboxResult.OnSuccess -> {
                    Log.i("Sanoop", "On Success -> ${it.successPayload}")
                }
                is FinboxResult.OnError -> {
                    Log.i("Sanoop", "On Error -> ${it.errorPayload}")
                }
            }
        })
```

1. `OnExit` When user exits the SDK 
2. `OnError` When any error occurs in the SDK this callback will be triggered.
3. `OnSuccess` When user completes the SDK flow and statement is uploaded this callback will be triggered.
