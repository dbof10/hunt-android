package com.ctech.eaty.ui.home.model

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel

class SuggestedProducts(sticky: StickyItemViewModel, products: List<ProductItemViewModel>) : HorizontalProduct(sticky, products)