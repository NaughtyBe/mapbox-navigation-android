package com.mapbox.services.android.navigation.v5.navigation;

import com.mapbox.navigator.Navigator;
import com.mapbox.navigator.RouterResult;

class MapboxOfflineRouter {
  private static final String EMPTY_TRANSLATIONS_DIR_PATH = "";
  private Navigator navigator;

  static {
    NavigationLibraryLoader.load();
  }

  MapboxOfflineRouter() {
    this(new Navigator());
  }

  MapboxOfflineRouter(Navigator navigator) {
    this.navigator = navigator;
  }

  /**
   * Configures the navigator for getting offline routes
   *
   * @param tilesPath directory path where the tiles are located
   * @param callback a callback that will be fired when the offline data is initialized and
   * {@link MapboxOfflineRouting#findOfflineRoute(OfflineRoute)} could be called safely
   */
  synchronized void configure(String tilesPath, OnOfflineDataInitialized callback) {
    new ConfigureRouterTask(navigator, tilesPath, EMPTY_TRANSLATIONS_DIR_PATH, callback).execute();
  }

  /**
   * Uses libvalhalla and local tile data to generate mapbox-directions-api-like json
   *
   * @param offlineRoute an offline navigation route
   * @return a RouterResult object with the json and a success/fail bool
   */
  synchronized RouterResult retrieveRouteFor(OfflineRoute offlineRoute) {
    String offlineUri = offlineRoute.buildUrl();
    return navigator.getRoute(offlineUri);
  }
}
