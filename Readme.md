# XML to TREE

Simple service for converting xml to tree.

It has gradle tasks for creating docker image with 
[gradle docker plugin](https://github.com/palantir/gradle-docker).

## Launching 

1. Clone this repository
2. Run ```gradle build``` to build app
3. Run ```docker run -p 8080:8080 xml-tree-service``` to start service

## Usage

Service is binding to port 8080 and handling POST requests.

To test service use ```curl -d @<path/to/xml> localhost:8080```

Example of reqeust:

```
<?xml version="1.0" encoding="UTF-8"?>
   <root>
       <level1 param="test">
           <level2>
               this is test xml
           </level2>
       </level1>
   </root>
```
Example of responce:

```
  |#document
  |  |root
  |  |  |level1
  |  |  |  |@param: test
  |  |  |  |level2
  |  |  |  |  |"this is test xml"
```
   