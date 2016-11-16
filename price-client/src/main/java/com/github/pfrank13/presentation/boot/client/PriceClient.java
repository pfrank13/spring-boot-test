package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;

/**
 * @author pfrank
 */
public interface PriceClient {
  PriceResponse findPriceByItemId(final int itemId);
}
