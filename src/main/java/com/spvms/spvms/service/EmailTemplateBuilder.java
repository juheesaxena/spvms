package com.spvms.spvms.service;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateBuilder {

    public String prSubmittedTemplate(String prNumber, String requesterName) {
        return "Hello " + requesterName + ",\n\n" +
                "Your Purchase Request " + prNumber + " has been successfully submitted.\n" +
                "Our team will review it shortly.\n\n" +
                "Regards,\nSPVMS Team";
    }

    public String prApprovedTemplate(String prNumber) {
        return "Hello,\n\n" +
                "Your Purchase Request " + prNumber + " has been APPROVED.\n" +
                "The procurement process will begin shortly.\n\n" +
                "Regards,\nSPVMS Team";
    }
}
