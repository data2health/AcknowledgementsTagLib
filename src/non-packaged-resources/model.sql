CREATE TABLE pubmed_central_ack_stanford.clinical_trial (
       pmcid INT NOT NULL
     , prefix TEXT NOT NULL
     , id TEXT NOT NULL
     , PRIMARY KEY (pmcid, prefix, id)
);

CREATE TABLE pubmed_central_ack_stanford.discipline (
       id INT NOT NULL
     , discipline TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.disease (
       id INT NOT NULL
     , disease TEXT
     , umls_id TEXT
     , umls_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.collaboration (
       id INT NOT NULL
     , collaboration TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.event (
       id INT NOT NULL
     , event TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.funding_agency (
       id INT NOT NULL
     , funding_agency TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.location (
       id INT NOT NULL
     , location TEXT
     , geonames_id INT
     , geonames_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.publication_component (
       id INT NOT NULL
     , publication_component TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.project (
       id INT NOT NULL
     , project TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.person (
       id INT NOT NULL
     , first_name TEXT
     , middle_name TEXT
     , last_name TEXT
     , title TEXT
     , appendix TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.organization (
       id INT NOT NULL
     , organization TEXT
     , grid_id TEXT
     , grid_match_string TEXT
     , geonames_id INT
     , geonames_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.organism (
       id INT NOT NULL
     , organism TEXT
     , umls_id TEXT
     , umls_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.organic_chemical (
       id INT NOT NULL
     , organic_chemical TEXT
     , umls_id TEXT
     , umls_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.technique (
       id INT NOT NULL
     , technique TEXT
     , umls_id TEXT
     , umls_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.support (
       id INT NOT NULL
     , support TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.resource (
       id INT NOT NULL
     , resource TEXT
     , umls_id TEXT
     , umls_match_string TEXT
     , alt_umls_id TEXT
     , alt_umls_match_string TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.award (
       id INT NOT NULL
     , award TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE pubmed_central_ack_stanford.collaborator (
       pmcid INT NOT NULL
     , collaboration_id INT NOT NULL
     , person_id INT NOT NULL
     , PRIMARY KEY (pmcid, collaboration_id, person_id)
     , CONSTRAINT FK_collaborator_1 FOREIGN KEY (collaboration_id)
                  REFERENCES pubmed_central_ack_stanford.collaboration (id)
     , CONSTRAINT FK_collaborator_2 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
);

CREATE TABLE pubmed_central_ack_stanford.funder (
       pmcid INT NOT NULL
     , organization_id INT NOT NULL
     , award_id INT NOT NULL
     , PRIMARY KEY (pmcid, organization_id, award_id)
     , CONSTRAINT FK_funder_1 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
     , CONSTRAINT FK_funder_2 FOREIGN KEY (award_id)
                  REFERENCES pubmed_central_ack_stanford.award (id)
);

CREATE TABLE pubmed_central_ack_stanford.awardee (
       pmcid INT NOT NULL
     , award_id INT NOT NULL
     , person_id INT NOT NULL
     , PRIMARY KEY (pmcid, award_id, person_id)
     , CONSTRAINT FK_awardee_1 FOREIGN KEY (award_id)
                  REFERENCES pubmed_central_ack_stanford.award (id)
     , CONSTRAINT FK_awardee_2 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
);

CREATE TABLE pubmed_central_ack_stanford.provider (
       pmcid INT NOT NULL
     , person_id INT NOT NULL
     , resource_id INT NOT NULL
     , PRIMARY KEY (pmcid, person_id, resource_id)
     , CONSTRAINT FK_provider_1 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
     , CONSTRAINT FK_provider_2 FOREIGN KEY (resource_id)
                  REFERENCES pubmed_central_ack_stanford.resource (id)
);

CREATE TABLE pubmed_central_ack_stanford.supporter (
       pmcid INT NOT NULL
     , support_id INT NOT NULL
     , organization_id INT NOT NULL
     , PRIMARY KEY (pmcid, support_id, organization_id)
     , CONSTRAINT FK_supporter_1 FOREIGN KEY (support_id)
                  REFERENCES pubmed_central_ack_stanford.support (id)
     , CONSTRAINT FK_supporter_2 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
);

CREATE TABLE pubmed_central_ack_stanford.organism_mention (
       organism_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (organism_id, pmcid)
     , CONSTRAINT FK_TABLE_24_1 FOREIGN KEY (organism_id)
                  REFERENCES pubmed_central_ack_stanford.organism (id)
);

CREATE TABLE pubmed_central_ack_stanford.technique_mention (
       technique_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (technique_id, pmcid)
     , CONSTRAINT FK_TABLE_25_1 FOREIGN KEY (technique_id)
                  REFERENCES pubmed_central_ack_stanford.technique (id)
);

CREATE TABLE pubmed_central_ack_stanford.support_mention (
       support_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (support_id, pmcid)
     , CONSTRAINT FK_TABLE_26_1 FOREIGN KEY (support_id)
                  REFERENCES pubmed_central_ack_stanford.support (id)
);

CREATE TABLE pubmed_central_ack_stanford.resource_mention (
       resource_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (resource_id, pmcid)
     , CONSTRAINT FK_TABLE_27_1 FOREIGN KEY (resource_id)
                  REFERENCES pubmed_central_ack_stanford.resource (id)
);

CREATE TABLE pubmed_central_ack_stanford.location_mention (
       location_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (location_id, pmcid)
     , CONSTRAINT FK_TABLE_28_1 FOREIGN KEY (location_id)
                  REFERENCES pubmed_central_ack_stanford.location (id)
);

CREATE TABLE pubmed_central_ack_stanford.disease_mention (
       disease_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (disease_id, pmcid)
     , CONSTRAINT FK_TABLE_29_1 FOREIGN KEY (disease_id)
                  REFERENCES pubmed_central_ack_stanford.disease (id)
);

CREATE TABLE pubmed_central_ack_stanford.organic_chemical_mention (
       organic_chemical_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (organic_chemical_id, pmcid)
     , CONSTRAINT FK_TABLE_30_1 FOREIGN KEY (organic_chemical_id)
                  REFERENCES pubmed_central_ack_stanford.organic_chemical (id)
);

CREATE TABLE pubmed_central_ack_stanford.collaboration_mention (
       collaboration_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (collaboration_id, pmcid)
     , CONSTRAINT FK_TABLE_31_1 FOREIGN KEY (collaboration_id)
                  REFERENCES pubmed_central_ack_stanford.collaboration (id)
);

CREATE TABLE pubmed_central_ack_stanford.award_mention (
       award_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (award_id, pmcid)
     , CONSTRAINT FK_TABLE_32_1 FOREIGN KEY (award_id)
                  REFERENCES pubmed_central_ack_stanford.award (id)
);

CREATE TABLE pubmed_central_ack_stanford.person_mention (
       person_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (person_id, pmcid)
     , CONSTRAINT FK_TABLE_33_1 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
);

CREATE TABLE pubmed_central_ack_stanford.organization_mention (
       organization_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (organization_id)
     , CONSTRAINT FK_TABLE_34_1 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
);

CREATE TABLE pubmed_central_ack_stanford.event_mention (
       event_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (event_id, pmcid)
     , CONSTRAINT FK_TABLE_35_1 FOREIGN KEY (event_id)
                  REFERENCES pubmed_central_ack_stanford.event (id)
);

CREATE TABLE pubmed_central_ack_stanford.publication_component_mention (
       publication_component_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (publication_component_id, pmcid)
     , CONSTRAINT FK_TABLE_36_1 FOREIGN KEY (publication_component_id)
                  REFERENCES pubmed_central_ack_stanford.publication_component (id)
);

CREATE TABLE pubmed_central_ack_stanford.discipline_mention (
       discipline_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (discipline_id, pmcid)
     , CONSTRAINT FK_TABLE_37_1 FOREIGN KEY (discipline_id)
                  REFERENCES pubmed_central_ack_stanford.discipline (id)
);

CREATE TABLE pubmed_central_ack_stanford.project_mention (
       project_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (project_id, pmcid)
     , CONSTRAINT FK_TABLE_39_1 FOREIGN KEY (project_id)
                  REFERENCES pubmed_central_ack_stanford.project (id)
);

CREATE TABLE pubmed_central_ack_stanford.funding_agency_mention (
       funding_agency_id INT NOT NULL
     , pmcid INT NOT NULL
     , PRIMARY KEY (funding_agency_id, pmcid)
     , CONSTRAINT FK_TABLE_40_1 FOREIGN KEY (funding_agency_id)
                  REFERENCES pubmed_central_ack_stanford.funding_agency (id)
);

CREATE TABLE pubmed_central_ack_stanford.investigator (
       pmcid INT NOT NULL
     , person_id INT NOT NULL
     , organization_id INT NOT NULL
     , PRIMARY KEY (pmcid, person_id, organization_id)
     , CONSTRAINT FK_investigator_1 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
     , CONSTRAINT FK_investigator_2 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
);

CREATE TABLE pubmed_central_ack_stanford.skill (
       pmcid INT NOT NULL
     , person_id INT NOT NULL
     , technique_id INT NOT NULL
     , PRIMARY KEY (pmcid, person_id, technique_id)
     , CONSTRAINT FK_TABLE_41_1 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
     , CONSTRAINT FK_TABLE_41_2 FOREIGN KEY (technique_id)
                  REFERENCES pubmed_central_ack_stanford.technique (id)
);

CREATE TABLE pubmed_central_ack_stanford.affiliation (
       pmcid INT NOT NULL
     , person_id INT NOT NULL
     , organization_id INT NOT NULL
     , PRIMARY KEY (pmcid, person_id, organization_id)
     , CONSTRAINT FK_affiliation_1 FOREIGN KEY (person_id)
                  REFERENCES pubmed_central_ack_stanford.person (id)
     , CONSTRAINT FK_affiliation_2 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
);

CREATE TABLE pubmed_central_ack_stanford.collaborant (
       pmcid INT NOT NULL
     , organization_id INT NOT NULL
     , collaboration_id INT NOT NULL
     , PRIMARY KEY (pmcid, organization_id, collaboration_id)
     , CONSTRAINT FK_collaborant_1 FOREIGN KEY (organization_id)
                  REFERENCES pubmed_central_ack_stanford.organization (id)
     , CONSTRAINT FK_collaborant_2 FOREIGN KEY (collaboration_id)
                  REFERENCES pubmed_central_ack_stanford.collaboration (id)
);

