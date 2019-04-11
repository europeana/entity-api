<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.html"%>
<% 
String pageDescription="JSONLD templates for creation of concept scheme objects";
String withType = request.getParameter("withType");
boolean hasType = withType != null;		
%>	
<%@include file="description.jspf"%>

<p>
The following properties are optional in all concept schemas:
<b>definition, sameAs.</b> 
</p>
					<ul id="toc">
						<li><a href="#conceptscheme">Create Concept Scheme</a></li>
					</ul>

<h3 id="conceptscheme">Create Concept Scheme</h3>
The json-ld serialization available in the following box is a valid input to be used for the creation of simple <b>concept scheme</b>.
&nbsp;&nbsp;&nbsp; <a href="#top">top</a> 
<textarea rows="18" cols="120" name="jsonldtag">
{
  "@context": "https://www.europeana.eu/schemas/context/entity.jsonld", 
  "type": "ConceptScheme",
  "prefLabel": {
     "en": "Photography Genre"
  },
  "definition": {
     "en": "Genres of Photography"
  },
  "isDefinedBy": "https://www.europeana.eu/api/entities/search.json?wskey=apidemo&pageSize=100&query=skos_broader:(*/106+OR+*/1719+OR+*/1683)",
  "sameAs": ["http://www.wikidata.org/entity/Q3100808"]
}
</textarea>

<br>

<br>			
<%@include file="footer.html"%>


