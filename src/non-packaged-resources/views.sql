create materialized view ack_by_total_entity as
select pmcid,sum(count)
from (
    select pmcid,count(*) from award_mention group by 1
union
    select pmcid,count(*) from collaboration_mention group by 1
union
    select pmcid,count(*) from discipline_mention group by 1
union
    select pmcid,count(*) from disease_mention group by 1
union
    select pmcid,count(*) from event_mention group by 1
union
    select pmcid,count(*) from funding_agency_mention group by 1
union
    select pmcid,count(*) from location_mention group by 1
union
    select pmcid,count(*) from organic_chemical_mention group by 1
union
    select pmcid,count(*) from organism_mention group by 1
union
    select pmcid,count(*) from organization_mention group by 1
union
    select pmcid,count(*) from person_mention group by 1
union
    select pmcid,count(*) from project_mention group by 1
union
    select pmcid,count(*) from publication_component_mention group by 1
union
    select pmcid,count(*) from resource_mention group by 1
union
    select pmcid,count(*) from support_mention group by 1
union
    select pmcid,count(*) from technique_mention group by 1
    ) as foo
group by 1 order by 2 desc;

create materialized view ack_by_distinct_entity as
select pmcid,count(*)
from (
    select distinct pmcid,'award_mention' from award_mention
union
    select distinct pmcid,'collaboration_mention' from collaboration_mention
union
    select distinct pmcid,'discipline_mention' from discipline_mention
union
    select distinct pmcid,'disease_mention' from disease_mention
union
    select distinct pmcid,'event_mention' from event_mention
union
    select distinct pmcid,'funding_agency_mention' from funding_agency_mention
union
    select distinct pmcid,'location_mention' from location_mention
union
    select distinct pmcid,'organic_chemical_mention' from organic_chemical_mention
union
    select distinct pmcid,'organization_mention' from organization_mention
union
    select distinct pmcid,'organization_mention' from organization_mention
union
    select distinct pmcid,'person_mention' from person_mention
union
    select distinct pmcid,'project_mention' from project_mention
union
    select distinct pmcid,'publication_component_mention' from publication_component_mention
union
    select distinct pmcid,'resource_mention' from resource_mention
union
    select distinct pmcid,'support_mention' from support_mention
union
    select distinct pmcid,'technique_mention' from technique_mention
    ) as foo
group by 1 order by 2 desc;