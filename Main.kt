import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlinx.coroutines.*
import kotlinx.browser.window

// ----- Data model -----
data class Port(
    val id: String,
    val nameEn: String,
    val nameFr: String,
    val statusEn: String,
    val statusFr: String,
    val isAvailable: Boolean
)

// ----- Fake API fetch -----
suspend fun fetchPorts(): List<Port> {
    delay(1000) // simulate API delay
    val ports = listOf(
        Port("1", "Montreal", "Montréal", "Available", "Disponible", true),
        Port("2", "Vancouver", "Vancouver", "Unavailable", "Indisponible", false),
        Port("3", "Halifax", "Halifax", "Available", "Disponible", true),
        Port("4", "Toronto", "Toronto", "Unavailable", "Indisponible", false)
    )
    // Random availability for demo
    return ports.map {
        val available = (0..1).random() == 1
        it.copy(
            isAvailable = available,
            statusEn = if (available) "Available" else "Unavailable",
            statusFr = if (available) "Disponible" else "Indisponible"
        )
    }
}

// ----- UI -----
@Composable
fun PortSelectionScreen() {
    var ports by remember { mutableStateOf<List<Port>>(emptyList()) }
    var showAvailableOnly by remember { mutableStateOf(false) }
    var currentLang by remember { mutableStateOf("en") }

    // Poll every 15 seconds
    LaunchedEffect(showAvailableOnly, currentLang) {
        while (true) {
            ports = fetchPorts().let {
                if (showAvailableOnly) it.filter { it.isAvailable } else it
            }
            delay(15_000)
        }
    }

    // Layout
    Div({
        style {
            padding(24.px)
            fontFamily("Arial")
            maxWidth(600.px)
            margin(0.px, LinearDimension.auto)
        }
    }) {
        H2 { Text(if (currentLang == "fr") "Sélection du port" else "Port Selection") }

        // Language Switch
        Button(attrs = {
            onClick { currentLang = if (currentLang == "en") "fr" else "en" }
            style {
                marginBottom(16.px)
                padding(8.px)
            }
        }) {
            Text(if (currentLang == "en") "Passer en français" else "Switch to English")
        }

        // Availability Toggle
        Label({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                marginBottom(16.px)
            }
        }) {
            Input(InputType.Checkbox, attrs = {
                onInput { showAvailableOnly = it.value == "on" }
            })
            Span({
                style { marginLeft(8.px) }
            }) {
                Text(if (currentLang == "fr") "Afficher uniquement disponibles" else "Show Available Only")
            }
        }

        // Ports List
        Ul {
            ports.forEach { port ->
                Li({
                    style {
                        display(DisplayStyle.Flex)
                        justifyContent(JustifyContent.SpaceBetween)
                        padding(8.px)
                        borderBottom(1.px, LineStyle.Solid, rgb(230, 230, 230))
                    }
                }) {
                    Span { Text(if (currentLang == "fr") port.nameFr else port.nameEn) }
                    Span({
                        style {
                            color(if (port.isAvailable) Color.green else Color.red)
                        }
                    }) {
                        Text(if (currentLang == "fr") port.statusFr else port.statusEn)
                    }
                }
            }
        }
    }
}

// ----- Entry Point -----
fun main() {
    renderComposable(rootElementId = "root") {
        PortSelectionScreen()
    }
}
