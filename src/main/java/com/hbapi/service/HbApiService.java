package com.hbapi.service;

import hbapi.model.ProductInfoModel;

public interface HbApiService {
    ProductInfoModel getBarcodeInfo(String barcode);
}
