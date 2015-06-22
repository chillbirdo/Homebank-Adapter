# Homebank-Adapter

This program acts as an adapter between Homebank and the bank that you are using in order to automate the data import process. Homebank is a free presonal finance Software. ( http://homebank.free.fr/ )

At the moment the only supported bank is easybank.at .
Feel free to contribute your bank by simply implementing the IImporter interface!

## Features:

- simply download the data file from easybank and start Homebank-Adapter. It will automatically import new records and store the processed file in a "processed" folder.
- a "workspace" provides you a definite directory structure and will continuously create backups of your actual .xhb file for safety.
