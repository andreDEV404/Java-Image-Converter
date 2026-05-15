# 🖼️ Java Image Converter
 
A lightweight Java CLI tool to batch-convert images between the most common formats, with automatic transparency handling and duplicate output management.
 
## ✨ Features
 
- 📂 Batch conversion — processes all images in the `input_images` folder at once
- 🎨 Transparency handling — automatically fills transparent backgrounds with white when converting to JPEG or BMP
- 🔄 No overwrites — adds a numeric suffix if a file with the same name already exists in the output folder
- ⚠️ GIF support — converts GIF files (first frame only, due to Java API limitations)
- 💻 Cross-platform — runs on Windows, macOS and Linux
## 📦 Supported Formats
 
`JPEG` `JPG` `PNG` `BMP` `GIF` `TIFF` `WBMP`
 
## 🚀 Getting Started
 
### Prerequisites
- Java Development Kit (JDK) **17 or higher**
### Installation
Clone the repository:
```bash
git clone https://github.com/andreDEV404/Java-Image-Converter.git
cd Java-Image-Converter
```
 
### Usage
1. **Compile** the source file:
   ```bash
   javac ImageConverter.java
   ```
 
2. **Run** the program:
   ```bash
   java ImageConverter
   ```
 
3. **Place** the images you want to convert in the `input_images` folder
4. **Enter** the target format when prompted
5. **Find** the converted images in the `output_images` folder
## 📁 Project Structure
 
```
Java-Image-Converter/
├── ImageConverter.java     # Main source file
├── input_images/           # Place your images here
├── output_images/          # Converted images will appear here
├── .gitignore
└── LICENSE
```
 
## 🛠️ Built With
 
- `javax.imageio` — image reading and writing
- `java.awt` — image manipulation and transparency handling
- `java.io` — file and directory management
## 📄 License
 
This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
 
