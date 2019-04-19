# parsepub

---

## **Overview**

**parsepub** is a universal tool written in Kotlin designed to convert an EPUB publication into a data model used later by a reader. In addition it also provides validation and a system of logs informing about the inconsistency of the format.  

---

## **Features**

* converting the publication to a model containing all resources and necessary information
* providing EPUB format support in versions 2.0 and 3.0 for all major tags
* handling inconsistency errors or lack of necessary elements in the publication structure
* support for displaying logs when element structure attributes are missing

---

## Restrictions
In order for program to work properly the EPUB file must be created in accordance with the format requirements.   
Spec for [EPUB 3.0](http://idpf.org/epub/30)  
Spec for [EPUB 2.1](http://idpf.org/epub/201)

---
## Base model - description
The EpubBook class contains all information from an uncompressed EPUB publication.
Each of the parameters corresponds to a set of information parsed from the elements of the publication structure.
```bash
data class EpubBook (
    val epubOpfFilePath: String? = null,
    val epubTocFilePath: String? = null,
    val epubCoverImage: EpubResourceModel? = null,
    val epubMetadataModel: EpubMetadataModel? = null,
    val epubManifestModel: EpubManifestModel? = null,
    val epubSpineModel: EpubSpineModel? = null,
    val epubTableOfContentsModel: EpubTableOfContentsModel? = null
)
```
*epubOpfFilePath* - Contains absolute path to the .opf file.  
*epubTocFilePath* - Contains absolute path to the .toc file.  
*epubCoverImage* - Contains all information about the publication cover image.  
*epubMetadataModel* - Contains all publication resources.  
*epubManifestModel* -  Contains all basic information about the publication.  
*epubSpineModel* -  Contains list of references in reading order.  
*epubTableOfContentsModel* - Contains table of contents of the publication.  

More info about the elements of the publication in the  
**"Information about epub format for non-developers"** section

## Quick start
To convert the selected EPUB publication, create an instance of the EpubParser class
```bash
val epubParser = EpubParser()
```
next call `parse` method on it 
```bash
epubParser.parse(inputPath, decompressPath)
```
This method returns an *EpubBook* class object and has two parameters:  
*inputPath* - the path to the EPUB file,  
*decompressPath* - path to the place where the file should be unpacked

### Error handling in the structure of the publication
The structure of the converted file may be incorrect for one main reason - no required elements of publications such as **Metadata, Manifest, Spine, Table of Contents**.

**Solution - ValidationListeners**  
To limit the unexpected effects of an incorrect structure, we can create an implementation for properly prepared listeners that will alert us when the format will be wrong.  
On the previously created instance of the *EpubParser()* class, we call the `setValidationListeners` method, in the body of which we create the implementation of our listeners.  
Each listener has been assigned to a specific element.
```bash
epubParser.setValidationListeners {
   setOnMetadataMissing { Log.e(ERROR_TAG, "Metadata missing") }
   setOnManifestMissing { Log.e(ERROR_TAG, "Manifest missing") }
   setOnSpineMissing { Log.e(ERROR_TAG, "Spine missing") }
   setOnTableOfContentsMissing { Log.e(ERROR_TAG, "Table of contents missing") }
} 
```

### Displaying missing attributes logs
Our parsing method can return unexpected results also when the set of attributes in the file structure element is not complete  
e.g. missing **language** attribute in **Metadata** element.

**Solution - MissingAttributeLogger**  
The mechanism that we created is the answer to the problem illustrated above and it is similar to ValidationListener.  
When the required attribute is not correct or missing, logger reports it along with its name and parent attribute name.  
As parameters, we receive two values:  
*parentElement* - the name of the main element in which the error occurs  
*attributeName* - name of the missing attribute

```bash
epubParser.setMissingAttributeLogger {
   setOnAttributeLogger { parentElement, attributeName ->
       Log.w("$parentElement warn", "missing $attributeName attribute")
   }
}
```

## Information about epub format for non-developers
**EPUB** is an e-book file format that uses the ".epub" file extension.
Its structure is based on the main elements, such as: **Metadata, Manifest, Spine, Table of Contents**.

**Metadata** - contains all metadata information for a specific EPUB file. Three metadata attributes are required (though many are still available):  
*title* - contains the title of the book. \
*language* - contains the language of the book, \
*identifier* - contains the unique identifier of the book.

```
<metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
   <dc:title id="title">Title of the book</dc:title>
   <dc:language>en</dc:language>
   <dc:identifier id="pub-id">id-identifier</dc:identifier>
```
**Manifest** - element lists all the files. Each file is represented by an element, and has the required attributes:  
*id* - id of the resource  
*href* - location of the resource  
*media-type* - type and format of the resource

**Spine** - element lists all the XHTML content documents in their linear reading order.  
  
**Table of contents** - contains the hierarchical table of contents for the EPUB file.  
A description of the full TOC specification can be found here:  
TOC spec for [EPUB 2.0](http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.4.1)  
TOC spec for [EPUB 3.0](https://www.idpf.org/epub/30/spec/epub30-contentdocs.html#sec-xhtml-nav)
