# Image Processing

## General Usage

This program can run via the GUI, accept input from the terminal, or run a script from a file.

To launch the GUI, run

```
java -jar ImageProcessing.jar
```

To prompt for input in a terminal, run

```
java -jar ImageProcessing.jar -text
```

To specify a script file, say, `res/script.txt`, run

```
java -jar ImageProcessing.jar res/script.txt
```

## Supported GUI Operations

When you first run the program, only the "Load" button will be available to you.
Click this button to open a file picker to choose the image you want to load into the program.
Next, you'll be prompted to name the image. You can navigate between the images you have
loaded by using the dropdown box at the top of the screen.

Once you have an image loaded, all commands should become available.

* **Load:** Loads an image from a file into the program. You will be prompted to choose an image file and specify a name for the image.
* **Save:** Saves the currently visible image to a file. You will be prompted to choose a location and name for the file.
* **Red, Green, Blue:** Use these buttons to visualize the red, green, and blue components of an image. The operation will be performed based on the currently visible image, and you will be prompted for a name for the resulting image.
* **Luma, Intensity, Value:** Use these buttons to create a grayscale image using one of the three methods. You will need to specify a name for the resulting image.
* **Brighten, Darken:** Brighten or darken the image by a specified amount. You will first be prompted for the amount to brighten or darken by, then for the name of the resulting image.
* **V. Flip, H. Flip:** Vertically flip or horizontally flip the image. You will be prompted for the name of the resulting image.
* **Blur, Sharpen:** Blur or sharpen the current image. You will be prompted to provide a name for the resulting image.
* **Grayscale, Sepia:** Apply a grayscale or sepia color transformation to the image. You will be prompted for a name for the resulting image.

## Supported Text Commands

### Load

Load an image from a file, inferring the image format from the file extension. PPM, PNG, JPEG, and BMP formats are supported.

```
load [filename] [image name]
```

Examples:
* `load res/koala.ppm koala`
* `load res/rocks.png rocks-image`
* `load res/boston.jpg boston-skyline`

You will need to load an image before running any other commands with it.

### Save

Save an image to a file, inferring the image format from the file extension. PPM, PNG, JPEG, and BMP formats are supported.

```
save [filename] [image name]
```
Examples:
* `save res/koala-sepia.ppm koala-sepia`
* `save res/rocks-red.png rocks-red-component`
* `save res/boston-flipped.jpg boston-vertical`

You will need to save any images before being able to view images that have been modified by a filter.


### Red, Green, and Blue Component

Visualizes each individual channel (either red, green, or blue) by creating a greyscale image.

```
red-component [image name] [destination name]
```
```
green-component [image name] [destination name]
```
```
blue-component [image name] [destination name]
```
Examples:
* `red-component koala koala-red-component`
* `green-component rocks-image rocks-green-component`
* `blue-component boston-skyline boston-blue-component`

### Intensity, Luma, and Value

Converts the image to grayscale using the luma, intensity, or value method.

* **Intensity:** The grayscale value is the average of each of the three (red, green, blue) components.
* **Luma:** The grayscale value is the sum of the red component multiplied by `0.2126`, the green component multiplied by `0.7152`, and the blue component multiplied by `0.0722`.
* **Value:** The grayscale value is the maximum of the three component values.

```
intensity [image name] [destination name]
```
```
luma [image name] [destination name]
```
```
value [image name] [destination name]
```

Examples:
* `intensity koala koala-intensity-image`
* `luma rocks-image rocks-luma`
* `value boston-skyline boston-skyline-gray`


### Brighten and Darken

Brightens or darkens an image by adding or subtracting the provided value from each component.

```
brighten [value] [image name] [destination name]
```
```
darken [value] [image name] [destination name]
```

Examples:
* `brighten koala koala-bright`
* `darken rocks rocks-dark`

### Blur and Sharpen

Blurs or sharpens an image using a Gaussian blur or a 5x5 sharpening kernel.

```
blur [image name] [destination name]
```
```
sharpen [image name] [destination name]
```

Examples:
* `blur koala koala-blurred`
* `sharpen boston-skyline skyline-sharp`


### Grayscale

Another way to convert the image to a greyscale using the luma.

```
grayscale [image name] [destination name]
```

Examples:
* `grayscale koala koala-greyscale`
* `grayscale rocks rocks-greyscale`
* `grayscale boston boston-greyscale`

### Sepia

Applies a "sepia tone" filter to the image.

```
sepia [image name] [destination name]
```

Examples:
* `sepia koala koala-sepia`
* `sepia rocks rocks-sepia`
* `sepia boston boston-sepia`
