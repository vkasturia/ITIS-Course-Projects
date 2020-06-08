#!/usr/bin/python
# -*- coding: utf-8 -*-

import HTML

import urllib2
from SPARQLWrapper import SPARQLWrapper, JSON, XML, N3, RDF

t = HTML.Table(header_row=['Serial No.','LAK Author', 'Organization', 'No. LAK Publications', 'LAK Publications', 'DBLP Author', 'DBLP Publications','No. DBLP Publications'])

match_counter = 0
row = 1
sparql = SPARQLWrapper("http://meco.l3s.uni-hannover.de:8890/sparql")

sparql.setQuery("""
PREFIX swrc:<http://swrc.ontoware.org/ontology#>
PREFIX foaf:<http://xmlns.com/foaf/0.1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX bibo:<http://purl.org/ontology/bibo/>
PREFIX schema:<http://schema.org/>

SELECT ?author_name ?organization_name count(?article) as ?num_article 
       GROUP_CONCAT(?article; SEPARATOR=", ") AS ?articles
       
WHERE{ 
       ?article a schema:Article.
       ?article foaf:maker ?author.
       ?author rdfs:label ?author_name.
       ?author swrc:affiliation ?organization.
       ?organization rdfs:label ?organization_name.
     }ORDER BY DESC(?num_article)
      LIMIT 150
      OFFSET 10
""")

sparql.setReturnFormat(JSON)
results = sparql.query().convert()


#print(results)

for result in results["results"]["bindings"]:
    if ("organization_name" in result):
        organization_name = result["organization_name"]["value"].encode('ascii','ignore')
        print(result["organization_name"]["value"])
    else: 
        organization_name = 'NONE'
    if ("num_article" in result):
        num_article = result["num_article"]["value"].encode('ascii','ignore')
        print(num_article)
    else:
        num_article = 'NONE'
    if ("articles" in result):
        articles = result["articles"]["value"].encode('ascii','ignore')
        print(result["articles"]["value"])
	if ("author_name" in result):
  		print(result["author_name"]["value"])
  		author_name_lak = result["author_name"]["value"].encode('ascii', 'ignore')
  		author_name_lak_str = '"'+author_name_lak +'"'
  		#print (author_name_lak)
  		
  		sparql2 = SPARQLWrapper("http://dblp.l3s.de/d2r/sparql")
  		
  		sparql2.setQuery("""
  		PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  		PREFIX foaf:<http://xmlns.com/foaf/0.1/>
  		
  		SELECT ?author ?publication
  		WHERE{
  		       ?author rdf:type foaf:Agent.
  		       ?author foaf:name """ + author_name_lak_str + """.
  		       ?publication foaf:maker ?author. 
  		     }
  		""")
  		sparql2.setReturnFormat(JSON)
  		results2 = sparql2.query().convert()
  		
  		dblp_publications =""
  		count_publications = 0
  		author_dblp = ""
  		print("-------------RESULTS2-------------match counter: ",match_counter)
  		#print(results2)
  		match_counter += 1
  		for result2 in results2["results"]["bindings"]:
  		    if ("author" in result2):
  		        author_dblp = result2["author"]["value"].encode('ascii','ignore')
  		        print ("DBLP Author" + author_dblp)
  		    else:
  		        author_dblp = 'NONE'
  		        print("Author_DBLP: " +author_dblp)
  		  
  		    if ("publication" in result2):
  		        publication = result2["publication"]["value"].encode('ascii','ignore') 
  		        print("Publication: " + publication)
  		        dblp_publications = dblp_publications +" , "
  		        dblp_publications = dblp_publications + publication
  		        count_publications += 1
  		    else:
  		        publication = 'NONE'   
  	else:
  	    author_name_lak = 'NONE'
  	
    t.rows.append([row, author_name_lak, organization_name, num_article, articles, author_dblp, dblp_publications, count_publications])
    row +=1
htmlcode = str(t)
print htmlcode
