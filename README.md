# ANDIE — A Non-Destructive Image Editor

A desktop image editor built in Java Swing that applies all operations non-destructively, preserving the original image at every step. Developed as a team project for COSC202 at the University of Otago.

**[Try it in your browser](https://aaaansi.github.io/Andie/)** — no install required, runs via CheerpJ.

<!-- Replace with your own screenshot: take a screenshot of ANDIE with an image loaded, save it as docs/screenshot.png -->
![ANDIE screenshot](docs/screenshot.png)

---

## Features

### Filters
- **Mean, Gaussian, Median, Sharpen** — classic convolution-based filters with configurable radius
- **Emboss** — directional emboss at 8 angles (multiples of 45°)
- **Sobel Edge Detection** — highlights edges in the image
- **Posterise** — reduces colour palette using k-means clustering, optimised for large images
- **Region filtering** — apply any filter to just a selected rectangular area

### Drawing Tools
- **Shapes** — rectangles, ovals drawn within a selected region
- **Lines** — diagonal, horizontal, and vertical lines
- **Text** — draw text with a font chooser onto the image
- **Colour picker & brush thickness** — configurable drawing settings

### Transformations
- **Rotate** — arbitrary rotation
- **Flip** — horizontal and vertical
- **Resize** — scale the image up or down
- **Crop** — crop to a rectangular mouse selection

### Colour Adjustments
- **Brightness & Contrast** — adjustable via sliders
- **Convert to Greyscale**

### Other
- **Undo / Redo** — full operation history
- **Macros** — record, save, and replay sequences of operations
- **Mouse selection** — click-and-drag rectangular region selection, zoom-aware
- **Keyboard shortcuts** for most actions

---

## Getting Started

### Prerequisites
- Java 17+ (JDK)

### Build & Run
```bash
# Compile
javac -d bin -cp "lib/*" src/cosc202/andie/**/*.java src/cosc202/andie/*.java

# Run
java -cp "bin:lib/*" cosc202.andie.Andie
```

### Keyboard Shortcuts

| Action | Shortcut |
|---|---|
| Emboss Filter | `Ctrl + E` |
| Sobel Filter | `Ctrl + H` |
| Posterise | `Ctrl + P` |
| Crop to Selection | `Shift + X` |
| Start Macro | `Ctrl + Q` |
| Stop Macro | `Ctrl + W` |
| Load Macro | `Ctrl + A` |

---

## Project Structure

```
src/cosc202/andie/
├── Andie.java              # Entry point & GUI setup
├── EditableImage.java      # Non-destructive image model
├── ImagePanel.java         # Image display & mouse selection
├── Colours/                # Brightness, contrast, greyscale
├── Draw/                   # Shapes, lines, text, colour picker
├── Filters/                # Mean, Gaussian, Median, Emboss, Sobel, etc.
├── Transformations/        # Rotate, flip, resize, crop
└── ViewActions/            # Zoom & view controls
```

---

## Contributors

- **Lydia Acton** — Filters (emboss, sobel, posterise, extended filters), macros, region filtering
- **Ella Taylor** — Drawing functions, mouse selection
- **Hamzah Alansi** — Crop, draw text, mouse selection
- **Callum Walker** — Extended filters, core filter implementations
- **Steven Mills** — Original ANDIE framework

## License

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)
