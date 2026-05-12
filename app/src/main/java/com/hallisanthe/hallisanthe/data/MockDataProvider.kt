package com.hallisanthe.hallisanthe.data

import com.hallisanthe.hallisanthe.Product

object MockDataProvider {
    fun getMockProducts(): List<Product> {
        return listOf(
            Product("p1", "Terracotta Planter", 250, "https://images.unsplash.com/photo-1611080541599-8c6dbde6ed28", "Pottery", "Ravi", "Channapatna", "Hand-molded clay planter"),
            Product("p2", "Hand-painted Vase", 1200, "https://images.unsplash.com/photo-1578500494198-246f612d3b3d", "Pottery", "Anil", "Bidar", "Intricate Bidriware style vase"),
            Product("p3", "Clay Water Pot", 150, "https://images.unsplash.com/photo-1590640346030-975926ec0f8a", "Pottery", "Suresh", "Gundlupet", "Traditional cool water storage"),
            Product("p4", "Terracotta Lamps", 80, "https://images.unsplash.com/photo-1543058869-70362f79029a", "Pottery", "Lakshmi", "Mysore", "Beautifully crafted oil lamps"),
            Product("p5", "Ceramic Serving Bowl", 450, "https://images.unsplash.com/photo-1574101150499-52382c42c94d", "Pottery", "Arun", "Ramanagara", "Modern touch on traditional clay"),
            Product("p6", "Clay Tea Cups (Set)", 300, "https://images.unsplash.com/photo-1576092768241-dec231879fc3", "Pottery", "Balu", "Molakalmuru", "Set of 6 traditional kulhads"),
            Product("t1", "Silk Handloom Saree", 4500, "https://images.unsplash.com/photo-1610030469983-98e550d6193c", "Textiles", "Meera", "Ilkal", "Traditional Ilkal silk saree"),
            Product("t2", "Cotton Hand-woven Towel", 200, "https://images.unsplash.com/photo-1620916566398-39f1143ab7be", "Textiles", "Kavita", "Gadag", "Pure soft handloom cotton"),
            Product("t3", "Embroidered Cushion", 650, "https://images.unsplash.com/photo-1584100936595-c0654b55a2e6", "Textiles", "Sita", "Hubli", "Hand-stitched folk patterns"),
            Product("t4", "Khadi Shirt", 950, "https://images.unsplash.com/photo-1598033129183-c4f50c7176c8", "Textiles", "Manju", "Dharwad", "Breathable organic khadi cotton"),
            Product("t5", "Woolen Shawl", 1500, "https://images.unsplash.com/photo-1606760227091-3dd870d97f1d", "Textiles", "Deepa", "Kodagu", "Warm hand-knitted hill wool"),
            Product("t6", "Block Print Dupatta", 1200, "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb", "Textiles", "Radha", "Bagalkot", "Natural dye block prints"),
            Product("w1", "Carved Wood Bowl", 850, "https://images.unsplash.com/photo-1610701596007-11502861dcfa", "Woodwork", "Kiran", "Sagar", "Rosewood hand-carved bowl"),
            Product("w2", "Bamboo Storage Basket", 180, "https://images.unsplash.com/photo-1591034351368-223d6118d533", "Woodwork", "Lata", "Sirsi", "Hand-woven durable bamboo"),
            Product("w3", "Wooden Toy Set", 400, "https://images.unsplash.com/photo-1539627831859-a911cf04d3cd", "Woodwork", "Basava", "Channapatna", "Lacquered organic wooden toys"),
            Product("w4", "Sandalwood Incense Stand", 320, "https://images.unsplash.com/photo-1602928292864-180a03001cae", "Woodwork", "Naveen", "Mysore", "Fragrant carved sandalwood"),
            Product("w5", "Teak Wood Spatula", 120, "https://images.unsplash.com/photo-1594385208934-2c356f91f53b", "Woodwork", "Shiv", "Shimoga", "Durable seasoned teak wood"),
            Product("w6", "Rosewood Jewelry Box", 2500, "https://images.unsplash.com/photo-1582139329536-e7284fece509", "Woodwork", "Mysore", "Inlaid with white wood patterns"),
            Product("o1", "Organic Forest Honey", 350, "https://images.unsplash.com/photo-1587049352846-4a222e784d38", "Organic", "Sidda", "Western Ghats", "Pure wild honey"),
            Product("o2", "Handmade Herbal Soap", 95, "https://images.unsplash.com/photo-1600857062241-99e5da7f21e2", "Organic", "Gauri", "Udupi", "Neem and Tulsi organic soap"),
            Product("o3", "Cold Pressed Coconut Oil", 280, "https://images.unsplash.com/photo-1590779033100-9f60705a2f3b", "Organic", "Prakash", "Mangalore", "Traditional stone-pressed oil"),
            Product("o4", "Spice Blend (Masala)", 150, "https://images.unsplash.com/photo-1596040033229-a9821ebd058d", "Organic", "Savita", "Sirsi", "Grandma's secret recipe spices"),
            Product("o5", "Dried Banana Chips", 85, "https://images.unsplash.com/photo-1599490659223-e153c073f867", "Organic", "Anant", "Kumta", "Salted crispy local banana chips"),
            Product("o6", "Natural Turmeric Powder", 180, "https://images.unsplash.com/photo-1615485240384-54bc9b4d8d9b", "Organic", "Rupa", "Thirthahalli", "Pure high-curcumin turmeric")
        )
    }
}