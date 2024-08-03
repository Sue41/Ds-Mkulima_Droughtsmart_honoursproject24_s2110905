package com.example.myapplication.Resources;

import java.util.HashMap;
import java.util.Map;

public class ReusableInforRepository {
    public static final Map<String, ReusableInfor> ReusableInforMap = new HashMap<>();

    static {
        ReusableInforMap.put("about", new ReusableInfor(
                "help_item_icon",
                "About Us",
                "At DroughtSmart, we specialize in innovative agricultural solutions, offering top-notch irrigation equipment, a smart crop suggestion app, and advanced disease scanning technology. Our mission is to enhance farming efficiency and productivity with cutting-edge, affordable solutions tailored to your needs."
        ));

        ReusableInforMap.put("terms_and_conditions", new ReusableInfor(
                "help_item_icon",
                "Terms and Conditions",
                "By using DroughtSmart's services, you agree to our Terms and Conditions. These terms govern your use of our irrigation solutions, crop suggestion app, and disease scanning technology. You must comply with all applicable laws and are responsible for your account activities. For more details, please refer to our full Terms and Conditions."
        ));

        ReusableInforMap.put("privacy_policy", new ReusableInfor(
                "privacy",
                "Privacy Policy",
                "At DroughtSmart, we are committed to safeguarding your privacy. We collect personal and technical data to provide, improve, and personalize our irrigation equipment, crop suggestion, and disease scanning services. We do not sell your data to third parties and implement robust security measures to protect it. For more details, please review our full Privacy Policy."
        ));
//        ReusableInfor.put("", new ReusableInfor(
//                "",
//                "",
//                ""
//        ));

    }

    public static class ReusableInfor {
        private final String icon;
        private final String title;
        private final String text;

        public ReusableInfor(String icon, String title, String text) {
            this.icon = icon;
            this.title = title;
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public String getIcon() {
            return icon;
        }

        public String getText() {
            return text;
        }
    }
}
