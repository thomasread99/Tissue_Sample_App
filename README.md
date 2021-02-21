# Tissue_Sample_App
## INTRODUCTION
The TissueSampleApp has been built to help keep track of tissue samples, with certain characteristics containted in a larger collection of samples. This app enables users to:
* Display all of the collections on the home page
* Show the associated samples for each collection when tapped on
* Create new collections
* Create new samples for a specific collection
* Delete collections and their associated samples

The app is written in Java and is for Android devices.

## REQUIREMENTS
The app requires an Android device running minimum Android 4.4 (KitKat). In order to view and modify the code, a Java IDE is necessary, with Android Studio being recommended.

## INSTALLATION
To install the app on a device, either:
* Open the .apk file in the Application folder on an Android device, and follow the on screen steps for installation
* Or open the TissueSampleApp project in Android Studio and run it, either on a virtual device or a physical one connected to your machine and with USB debugging enabled. For more information, please refer to the official documentation [here](https://developer.android.com/training/basics/firstapp/running-app)

## CONFIGURATION
No additional configuration should be required to run the app, and no extra permissions are needed to do so.

## APPLICATION INFORMATION
The code for the app can be found in `Tissue_Sample_App\TissueSampleApp\app\src\main\java\com\readex\tissuesampleapp`, or if using Android Studio in `App\java\com.readex.tissuesampleapp`. The classes are split into folders to help with organisation and readability.

### ACTIVITIES
In the activities folder are the classes that control each Android activity - the currently displayed "screen". `AddCollectionActivity` and `AddSampleActivity` are what control the activities that allow the user to add new collections and samples respectivley, and the `ViewCollectionActivity` displays the collection which the user has tapped on.
<br>
The `MainActivity` is outside of the folder, as it needs to be for the app to work, but this is what controls the launch screen, and displays a list of all collections.

### ADAPTERS
An adapter is a class that acts as a bridge between the UI and a data source, and for this app they can be found in the adapters folder. The `CollectionAdapter` and `SampleAdapter` control the custom list views that display all of the collections or samples respectivley, and the `DatabaseAdapter` helps to perform any database operations, acting as a bridge between activities and the content provider.

### DATABASE
The database folder contains all classes relating to the apps database, namely the content provider and its contract. A content provider helps manage access to the database, by acting as an API to which you pass URIs to access the database content. `TissueSampleProvider` is the content provider for this app, with `TissueSampleProviderContract` acting as its contract, to provide the necessary URIs to pass to the content provider.

### MODELS
Custom objects are kept in the models folder. Only two custom objects are used in this app: `Collection` and `Sample`. These provide a way of storing data from the database within a Java object, that can be used within the activities and displayed to the user.

### UTILS
Helper classes are kept in the utils folder. The `DatabaseHelper` is the only one used for this app, which creates the database and populates it with initial values when the app is first used.

### LAYOUTS
UI elements for the application are stored in the layour folder under res. These are XML files that define the structure for each of the activities and their components.

## TROUBLESHOOTING
If you have any problems with running or using this application, please do not hesistate to get in touch.