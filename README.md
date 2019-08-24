# Benedict

## About Benedict

`Bene` + `dict`

`Bene` : 라틴어로 'good' [Bene](https://en.wiktionary.org/wiki/bene#Latin)

`dict` : dictionary

## Elasticsearch

```
DELETE /benedict

PUT benedict?include_type_name=false
{
  "mappings": {
    "properties": {
      "methodName": {
        "type": "text",
        "analyzer": "method_name_analyzer",
        "fielddata": true, 
        "fields": {
          "ngram": {
            "type": "text",
            "analyzer": "method_name_ngram_analyzer"
          }
        }
        
      },
      "methodSignature": {
        "type": "keyword",
        "doc_values": false,
        "index": false
      }
    }  
  },
  "settings": {
    "index": {
      "number_of_shards": 1,
      "number_of_replicas": 0
    },
    "analysis": {
      "analyzer": {
        "method_name_analyzer": {
          "tokenizer": "camel_case_tokenizer",
          "filter": [ "lowercase" ]
        },
        "method_name_ngram_analyzer": {
          "tokenizer": "camel_case_tokenizer",
          "filter": [ "lowercase", "edge_ngram_filter" ]
        }
      },
      "tokenizer": {
        "camel_case_tokenizer": {
          "type": "pattern",
          "pattern": "([^\\p{L}\\d]+)|(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=[\\p{L}&&[^\\p{Lu}]])(?=\\p{Lu})|(?<=\\p{Lu})(?=\\p{Lu}[\\p{L}&&[^\\p{Lu}]])"
        }
      },
      "filter": {
        "edge_ngram_filter": {
          "type": "edge_ngram",
          "min_gram": 2,
          "max_gram": 10
        }
      }
    }
  }
}

GET benedict

POST benedict/_search
{
  "query": {
    "match_all": {}
  }
}

GET benedict/_search
{
  "size": 0,
  "query": {
    "match": {
      "methodName.ngram": "get"
    }
  },
  "aggs": {
    "dedub": {
      "terms": {
        "field": "methodName"
      },
      "aggs": {
        "dedub_docs": {
          "top_hits": {
            "size": 1
          }
        }
      }
    }
  }
}

```
