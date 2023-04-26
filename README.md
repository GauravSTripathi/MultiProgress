<p align="center">
  <h3 align="center">Multi Progress Bar</h3>

  <p align="center">
    A simple library to create an hourly-based progress bar.
    <br/>
    <br/>
  </p>
</p>



## About The Project

![Screen Shot](https://github.com/GauravSTripathi/MultiProgress/blob/master/Screenshot_1.png)

A simple library to create an hourly-based progress bar.

## Getting Started


### Installation

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.GauravSTripathi:MultiProgress:1.0.0'
	}
```
## Usage

**XML**
```
<com.gaurav.multiprogressbar.MultiProgressBar
                    android:id="@+id/multi_progress_task_one"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/_40sdp"
                    android:layout_margin="@dimen/_5sdp"
                    app:gst_count="5"
                    app:gst_data_format="HOUR"
                    app:gst_end_hour="8"
                    app:gst_primary_progress_color="#4DD9D9D9"
                    app:gst_seconder_progress_color="#0392F3"
                    app:gst_start_hour="23"
                    app:gst_textColor="@color/white"
                    app:gst_textSize="20" />
```

**set progress**
```
var pair = ArrayList<Pair<Float, Float>>()

pair.add(Pair(0F, 2F))
pair.add(Pair(3F, 4F))
pair.add(Pair(6F, 8F))
binding.multiProgressTaskOne.setProgress(pair)
```
## License

Distributed under the MIT License. See [LICENSE](https://github.com//Multi Progress Bar/blob/main/LICENSE.md) for more information.

## Authors

* **Gaurav Tripathi** - *Native Android Software Engineer* - [Gaurav Tripathi](https://github.com/GauravSTripathi) - **
