package com.nm.libra.search

import org.compass.core.engine.SearchEngineQueryParseException

class SearchController {

  def searchableService

  def index = {
    log.info "Searching with " + params
    if (!params.q?.trim()) {
          return [:]
      }
      try {
          return [searchResult: searchableService.search(params.q)]
      } catch (SearchEngineQueryParseException ex) {
          return [parseException: true]
      }
  }

}
