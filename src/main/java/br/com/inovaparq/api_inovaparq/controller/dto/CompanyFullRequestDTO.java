package br.com.inovaparq.api_inovaparq.controller.dto;

public class CompanyFullRequestDTO {
    private CompanyData companyData;
    private AddressData addressData;
    private ResponsibleData responsibleData;

    // Getters e Setters

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    public ResponsibleData getResponsibleData() {
        return responsibleData;
    }

    public void setResponsibleData(ResponsibleData responsibleData) {
        this.responsibleData = responsibleData;
    }

    // Classes internas para os dados
    public static class CompanyData {
        private String companyName;
        private String cnpj;
        private String stateRegistration;
        private String municipalRegistration;
        private String corporateName;
        private String tradeName;
        private String phone;
        private String email;
        private String website;
        private String foundingDate;
        private String legalNature;
        private String businessActivity;
        private String operatingLicense; // Suporte para base64 ou link
        private String registrationDocument;
        private String addressProof;

        // Getters e Setters
        // ...gerar todos os getters e setters...
        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
        public String getCnpj() { return cnpj; }
        public void setCnpj(String cnpj) { this.cnpj = cnpj; }
        public String getStateRegistration() { return stateRegistration; }
        public void setStateRegistration(String stateRegistration) { this.stateRegistration = stateRegistration; }
        public String getMunicipalRegistration() { return municipalRegistration; }
        public void setMunicipalRegistration(String municipalRegistration) { this.municipalRegistration = municipalRegistration; }
        public String getCorporateName() { return corporateName; }
        public void setCorporateName(String corporateName) { this.corporateName = corporateName; }
        public String getTradeName() { return tradeName; }
        public void setTradeName(String tradeName) { this.tradeName = tradeName; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getWebsite() { return website; }
        public void setWebsite(String website) { this.website = website; }
        public String getFoundingDate() { return foundingDate; }
        public void setFoundingDate(String foundingDate) { this.foundingDate = foundingDate; }
        public String getLegalNature() { return legalNature; }
        public void setLegalNature(String legalNature) { this.legalNature = legalNature; }
        public String getBusinessActivity() { return businessActivity; }
        public void setBusinessActivity(String businessActivity) { this.businessActivity = businessActivity; }
        public String getOperatingLicense() { return operatingLicense; }
        public void setOperatingLicense(String operatingLicense) { this.operatingLicense = operatingLicense; }
        public String getRegistrationDocument() { return registrationDocument; }
        public void setRegistrationDocument(String registrationDocument) { this.registrationDocument = registrationDocument; }
        public String getAddressProof() { return addressProof; }
        public void setAddressProof(String addressProof) { this.addressProof = addressProof; }
    }

    public static class AddressData {
        private String cep;
        private String country;
        private String state;
        private String city;
        private String address;
        private String neighborhood;
        private String number;
        private String complement;

        // Getters e Setters
        // ...gerar todos os getters e setters...
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getNeighborhood() { return neighborhood; }
        public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
        public String getNumber() { return number; }
        public void setNumber(String number) { this.number = number; }
        public String getComplement() { return complement; }
        public void setComplement(String complement) { this.complement = complement; }
    }

    public static class ResponsibleData {
        private Boolean isNew;
        private String userId;
        private String name;
        private String email;
        private String cpf;
        private String phone;
        private String birthDate;
        private String role;

        // Getters e Setters
        // ...gerar todos os getters e setters...
        public Boolean getIsNew() { return isNew; }
        public void setIsNew(Boolean isNew) { this.isNew = isNew; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getBirthDate() { return birthDate; }
        public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}