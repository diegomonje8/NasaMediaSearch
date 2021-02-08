package es.nauticapps.nasamediasearch.datalayer

data class MediaModel(
    val collection: NasaCollection
)

data class NasaCollection(
    val href: String,
    val items: List<NasaItem>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)

data class NasaItem(
    val `data`: List<NasaData>,
    val href: String,
    val links: List<NasaLink>
)

data class LinkX(
    val href: String,
    val prompt: String,
    val rel: String
)

data class Metadata(
    val total_hits: Int
)

data class NasaData(
    val album: List<String>,
    val center: String,
    val date_created: String,
    val description: String,
    val description_508: String,
    val keywords: List<String>,
    val location: String,
    val media_type: String,
    val nasa_id: String,
    val photographer: String,
    val secondary_creator: String,
    val title: String
)

data class NasaLink(
    val href: String,
    val rel: String,
    val render: String
)