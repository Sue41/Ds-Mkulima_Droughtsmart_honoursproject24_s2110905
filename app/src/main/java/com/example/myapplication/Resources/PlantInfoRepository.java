package com.example.myapplication.Resources;

import java.util.HashMap;
import java.util.Map;

public class PlantInfoRepository {
    public static final Map<String, PlantInfo> plantInfoMap = new HashMap<>();

    static {
        plantInfoMap.put("Apple", new PlantInfo(
                "Apples are one of the most popular and widely cultivated fruits in the world. They come in various colors, including red, green, and yellow. Apples are typically grown in temperate climates and are known for their crisp texture and sweet to tart flavor. They are consumed fresh, cooked, or as part of various dishes such as pies, sauces, and juices.",
                "Apples are a good source of dietary fiber, vitamin C, and various antioxidants. They contain a type of fiber called pectin, which can help improve digestion and support heart health. Apples also provide vitamins A and B-complex vitamins, as well as minerals like potassium.",
                "Apples are usually planted in the spring or fall. The best time to plant apple trees is during the dormant season, typically in late winter or early spring before the buds break, or in the fall after leaf drop but before the ground freezes."
        ));

        plantInfoMap.put("Banana", new PlantInfo(
                "Bananas are a tropical fruit that grows in clusters on large banana plants. They have a distinct elongated shape and are covered with a thick, inedible peel that turns from green to yellow as it ripens. Bananas are known for their sweet taste and soft, creamy texture when ripe. They are widely consumed fresh or used in cooking and baking.",
                "Bananas are rich in potassium, which is essential for maintaining proper heart and muscle function. They also provide vitamin B6, vitamin C, and dietary fiber. Bananas are a good source of energy due to their natural sugars and carbohydrates, making them a popular choice for athletes and those needing a quick energy boost.",
                "Bananas can be planted throughout the year in tropical climates. However, the best time to plant bananas is during the rainy season, which helps establish the plants with adequate water supply."
        ));

        plantInfoMap.put("Blackgram", new PlantInfo(
                "Blackgram, also known as urad bean, is a type of legume widely grown in South Asia. The plants produce small, black seeds that are used in various culinary dishes, especially in Indian cuisine. Blackgram is often used to make dal, a type of lentil soup, and is also used in making dosa and idli batter.",
                "Blackgram is rich in protein, making it an excellent source of plant-based protein for vegetarians. It also provides dietary fiber, iron, calcium, and other essential nutrients. Consuming blackgram can help improve digestion, support bone health, and provide sustained energy.",
                "Blackgram can be planted during the Kharif season (June-July) or the Rabi season (October-November), depending on the regional climate. It is typically grown in warm, humid conditions with well-drained soil."
        ));

        plantInfoMap.put("ChickPea", new PlantInfo(
                "Chickpeas, also known as garbanzo beans, are a type of legume with a round shape and a nutty flavor. They are a staple ingredient in Mediterranean and Middle Eastern cuisines, used in dishes such as hummus, falafel, and various stews and salads.",
                "Chickpeas are high in protein, fiber, and various vitamins and minerals, including folate, iron, phosphorus, and manganese. They are a great source of plant-based protein and can help with weight management, improve digestion, and support heart health.",
                "Chickpeas are typically planted in the cool season, either in early spring or fall. They prefer well-drained soil and can be grown in various climatic conditions but thrive best in temperate regions."
        ));

        plantInfoMap.put("Coconut", new PlantInfo(
                "Coconuts are the fruit of the coconut palm, grown in tropical regions. They have a hard, fibrous shell and a white, edible interior known as the coconut meat. Coconuts are used in various culinary and non-culinary applications, including coconut milk, oil, and water.",
                "Coconuts are rich in healthy fats, particularly medium-chain triglycerides (MCTs), which can provide a quick source of energy. They also contain fiber, vitamins C and E, B vitamins, and several essential minerals such as potassium, iron, and magnesium.",
                "Coconuts can be planted throughout the year in tropical climates. They require well-drained sandy soil and a warm, humid environment. Regular watering and sunlight are crucial for the healthy growth of coconut palms."
        ));

        plantInfoMap.put("Coffee", new PlantInfo(
                "Coffee is a beverage made from roasted coffee beans, which are the seeds of berries from the Coffea plant. Coffee plants are typically grown in tropical and subtropical regions. The beverage is known for its stimulating effect due to its caffeine content.",
                "Coffee contains caffeine, a natural stimulant that can help improve focus and mental alertness. It also provides antioxidants, such as chlorogenic acid, which have various health benefits. Moderate coffee consumption is associated with a lower risk of certain diseases, including Parkinson's and Alzheimer's.",
                "Coffee plants are usually planted during the rainy season, typically around May-June. They require a warm climate with moderate rainfall and prefer well-drained, fertile soil. Shade and protection from direct sunlight can help in the healthy growth of coffee plants."
        ));

        plantInfoMap.put("Cotton", new PlantInfo(
                "Cotton is a soft fiber that grows around the seeds of the cotton plant, a shrub native to tropical and subtropical regions around the world. The fiber is spun into yarn or thread and used to make a soft, breathable textile. Cotton is one of the most important and widely used natural fibers in the textile industry.",
                "Cotton itself is not consumed directly, but cottonseed oil is extracted from the seeds and used in cooking and food products. Cotton fibers are highly valued for their comfort, durability, and absorbency, making them ideal for clothing and household textiles.",
                "Cotton is typically planted in the spring season (April-May). It requires a warm climate with plenty of sunshine and well-drained, fertile soil. Adequate irrigation is essential for the growth of cotton plants, particularly during the flowering and boll formation stages."
        ));

        plantInfoMap.put("Grapes", new PlantInfo(
                "Grapes are a type of fruit that grows in clusters on grapevines. They come in various colors, including green, red, and black, and can be eaten fresh or used to make wine, juice, jelly, and dried as raisins. Grapes are known for their sweet and tart flavors and are a popular fruit worldwide.",
                "Grapes are rich in vitamins C and K, and antioxidants, such as resveratrol, which have been linked to numerous health benefits, including heart health and anti-aging effects. They also provide a good amount of dietary fiber, which supports digestion.",
                "Grapes are typically planted in the spring season (March-April). They thrive in warm, sunny climates with well-drained soil. Proper pruning and training of grapevines are essential for healthy growth and optimal fruit production."
        ));

        plantInfoMap.put("Jute", new PlantInfo(
                "Jute is a long, soft, shiny vegetable fiber that can be spun into coarse, strong threads. It is primarily grown in India and Bangladesh and is used to make burlap, hessian, and other rough cloth. Jute plants grow quickly and are often referred to as the 'golden fiber' due to their color and value.",
                "Jute is not consumed directly as food. However, it plays a significant role in the agricultural and industrial sectors, particularly for making eco-friendly packaging materials, textiles, and geotextiles. Jute cultivation also helps in soil conservation and improvement.",
                "Jute is typically planted in the monsoon season (June-July). It requires a warm and humid climate with plenty of rainfall. Jute plants grow best in alluvial soil found in river deltas, where the soil is rich in nutrients."
        ));

        plantInfoMap.put("KidneyBeans", new PlantInfo(
                "Kidney beans are a type of legume named for their kidney shape and are commonly used in various cuisines worldwide. They are often used in dishes such as chili, soups, and salads. Kidney beans come in different colors, including red, white, and black.",
                "Kidney beans are high in protein, fiber, and essential nutrients such as folate, iron, and magnesium. They are a great source of plant-based protein and can help regulate blood sugar levels, improve digestion, and support heart health.",
                "Kidney beans are typically planted in the spring season (March-April). They require well-drained soil and a sunny location. Adequate watering and proper spacing are essential for healthy growth and development."
        ));
        plantInfoMap.put("Lentil", new PlantInfo(
                "Lentils are a type of legume that are small, lens-shaped seeds and are known for their quick cooking time and versatility in dishes. They come in various colors, including green, brown, red, and black. Lentils are a staple in many cuisines around the world, especially in South Asian and Middle Eastern cooking.",
                "Lentils are a powerhouse of nutrition, providing a high amount of protein, dietary fiber, iron, and folate. They are also a good source of manganese, phosphorus, and potassium. Lentils can help improve heart health, support digestive health, and provide sustained energy.",
                "Lentils are typically planted in early spring when the soil can be worked. They prefer cooler temperatures and can be sown as soon as the soil has thawed and can be tilled. Lentils grow best in well-drained soil with full sun exposure."
        ));

        plantInfoMap.put("Maize", new PlantInfo(
                "Maize, also known as corn, is a cereal grain that is one of the most widely grown staple foods in the world. It can be consumed as a vegetable when harvested young or as a grain when fully matured. Maize is used in a variety of products, including flour, cornmeal, corn syrup, and popcorn.",
                "Maize is a good source of carbohydrates, providing a significant amount of energy. It also contains dietary fiber, vitamins B and C, and essential minerals like magnesium and phosphorus. Maize supports digestive health and provides essential nutrients for overall health.",
                "Maize is usually planted in the spring after the last frost when the soil temperature is at least 60°F (15°C). It requires well-drained, fertile soil and full sun. Adequate spacing and regular watering are essential for healthy growth and high yields."
        ));

        plantInfoMap.put("Mango", new PlantInfo(
                "Mangoes are a tropical stone fruit known for their sweet, juicy flesh and distinct aroma. They come in various shapes and sizes, with colors ranging from green to yellow to red. Mangoes are enjoyed fresh, in smoothies, salads, and various culinary dishes worldwide.",
                "Mangoes are rich in vitamins A and C, which are important for immune function, skin health, and vision. They also provide dietary fiber, folate, and several antioxidants. Consuming mangoes can help improve digestion, boost immunity, and support overall health.",
                "Mango trees are typically planted in the spring when the soil has warmed. They require a warm, tropical climate with well-drained soil and full sun exposure. Regular watering, especially during the dry season, is crucial for the healthy growth of mango trees."
        ));

        plantInfoMap.put("MothBeans", new PlantInfo(
                "Moth beans are a type of legume commonly grown in arid and semi-arid regions of India. They produce small, brown seeds that are used in various traditional dishes. Moth beans are drought-resistant and can thrive in harsh climatic conditions.",
                "Moth beans are an excellent source of plant-based protein, dietary fiber, and essential minerals such as calcium, magnesium, and phosphorus. They are also rich in vitamins B and C. Moth beans can help improve digestion, support bone health, and provide sustained energy.",
                "Moth beans are typically planted during the Kharif season (July-August) in regions with low rainfall. They require well-drained sandy or loamy soil and full sun exposure. Moth beans are highly adaptable and can grow in poor soil conditions."
        ));

        plantInfoMap.put("MungBean", new PlantInfo(
                "Mung beans are small, green legumes that are widely used in Asian cuisine. They can be eaten whole, split, or as sprouts. Mung beans are known for their quick cooking time and versatility in dishes such as soups, salads, and desserts.",
                "Mung beans are rich in protein, dietary fiber, antioxidants, and essential vitamins and minerals such as folate, iron, and magnesium. They are low in calories and have been shown to support weight management, improve digestion, and boost heart health.",
                "Mung beans are typically planted in the spring or summer. They thrive in warm, moist conditions and require well-drained soil. Mung beans can be grown as a short-duration crop and are often used in crop rotation to improve soil fertility."
        ));

        plantInfoMap.put("Muskmelon", new PlantInfo(
                "Muskmelons, also known as cantaloupes, are a type of melon with a sweet, orange flesh and a netted rind. They are popular in summer due to their refreshing taste and high water content. Muskmelons are often eaten fresh, in fruit salads, or as a dessert.",
                "Muskmelons are a good source of vitamins A and C, which are important for immune function, skin health, and vision. They also provide dietary fiber, potassium, and several antioxidants. Consuming muskmelons can help keep you hydrated, support immune health, and improve digestion.",
                "Muskmelons are typically planted in the spring after the last frost when the soil temperature is at least 70°F (21°C). They require well-drained, sandy loam soil and full sun exposure. Regular watering and proper spacing are essential for healthy growth and fruit development."
        ));

        plantInfoMap.put("Orange", new PlantInfo(
                "Oranges are a type of citrus fruit known for their sweet and tangy flavor. They are one of the most popular fruits worldwide and are consumed fresh, juiced, or used in various culinary dishes and desserts. Oranges are recognized for their vibrant color and juicy flesh.",
                "Oranges are an excellent source of vitamin C, which is important for immune function, skin health, and collagen synthesis. They also provide dietary fiber, folate, and several antioxidants. Oranges can help boost immune health, improve skin health, and support heart health.",
                "Orange trees are typically planted in the spring when the soil has warmed. They require a warm, sunny climate with well-drained soil. Regular watering and fertilization are essential for the healthy growth and fruit production of orange trees."
        ));

        plantInfoMap.put("Papaya", new PlantInfo(
                "Papayas are tropical fruits with a sweet, musky flavor and a soft, butter-like consistency. They are usually pear-shaped and have a yellow or orange flesh with small black seeds in the center. Papayas are enjoyed fresh, in smoothies, or as part of various culinary dishes.",
                "Papayas are rich in vitamins C and A, folate, and dietary fiber. They also contain an enzyme called papain, which aids in digestion. Consuming papayas can help improve digestion, boost immune health, and support skin health.",
                "Papayas can be planted throughout the year in tropical and subtropical regions. They require well-drained, fertile soil and full sun exposure. Regular watering and protection from strong winds are essential for the healthy growth of papaya trees."
        ));

        plantInfoMap.put("PigeonPeas", new PlantInfo(
                "Pigeon peas are a type of legume commonly grown in tropical and subtropical regions. They are used as a food crop and for soil improvement due to their nitrogen-fixing properties. Pigeon peas are often used in soups, stews, and various traditional dishes.",
                "Pigeon peas are a good source of protein, dietary fiber, and essential vitamins and minerals such as folate, iron, and magnesium. They can help improve digestion, support heart health, and provide sustained energy. Pigeon peas are also known for their drought-resistant properties.",
                "Pigeon peas are typically planted during the rainy season (June-July) in tropical regions. They require well-drained soil and full sun exposure. Pigeon peas can be grown as an annual or perennial crop, depending on the climate and growing conditions."
        ));

        plantInfoMap.put("Pomegranate", new PlantInfo(
                "Pomegranates are a type of fruit known for their juicy, ruby-red seeds called arils. They have a sweet and tart flavor and are often used in juices, salads, and various culinary dishes. Pomegranates are celebrated for their health benefits and unique taste.",
                "Pomegranates are rich in vitamins C and K, dietary fiber, and antioxidants, particularly punicalagins and anthocyanins. They have been linked to numerous health benefits, including improved heart health, reduced inflammation, and better digestion.",
                "Pomegranate trees are typically planted in the spring or fall. They require well-drained soil and full sun exposure. Pomegranates are relatively drought-tolerant and can grow in a variety of soil types, making them a versatile fruit crop."
        ));

        plantInfoMap.put("Rice", new PlantInfo(
                "Rice is a staple food for more than half of the world's population. It is a cereal grain that is consumed in various forms, including white, brown, and wild rice. Rice is used in a wide range of dishes, from sushi to risotto, and is a fundamental ingredient in many cuisines.",
                "Rice is a good source of carbohydrates, providing a significant amount of energy. Brown rice, in particular, is rich in dietary fiber, vitamins B and E, and essential minerals such as magnesium and phosphorus. Rice is also gluten-free, making it suitable for those with gluten sensitivities.",
                "Rice is typically planted in the spring or early summer. It requires a warm climate, ample water supply, and well-drained, fertile soil. Rice paddies are often flooded to provide the necessary water for growth and to control weeds."
        ));

        plantInfoMap.put("Watermelon", new PlantInfo(
                "Watermelons are a large, sweet fruit with a high water content, making them a popular choice for hot summer days. They have a green rind and red or pink flesh filled with black seeds. Watermelons are enjoyed fresh, in fruit salads, or as a refreshing juice.",
                "Watermelons are a good source of vitamins A and C, which are important for immune function and skin health. They also contain antioxidants such as lycopene and citrulline, which can support heart health and reduce inflammation. Watermelons are hydrating and low in calories.",
                "Watermelons are typically planted in the spring after the last frost when the soil temperature is at least 70°F (21°C). They require well-drained, sandy loam soil and full sun exposure. Regular watering and proper spacing are essential for healthy growth and fruit development."
        ));
        // Add similar entries for the remaining plants...
    }
    public static class PlantInfo {
        private final String description;
        private final String nutrition;
        private final String plantingTime;

        public PlantInfo(String description, String nutrition, String plantingTime) {
            this.description = description;
            this.nutrition = nutrition;
            this.plantingTime = plantingTime;
        }

        public String getDescription() {
            return description;
        }

        public String getNutrition() {
            return nutrition;
        }

        public String getPlantingTime() {
            return plantingTime;
        }
    }
}
