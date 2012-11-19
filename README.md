# JSONCompactor

Compress JSON files by removing unnecessary white space.

Pretty basic, but does a good job (~12-18% compression for most files). Takes around 4 minutes per GB on a typical machine.

## Usage:
    java JSONCompactor /path/to/input/file.json /path/to/output/file.json

Note: Output file will be overwritten if it exists.