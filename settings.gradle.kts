rootProject.name = "CustomEntity"

val prefix = "customentity"
val core = "$prefix-core"

include(
    "$prefix-api",
    "$prefix-core",
    "$prefix-debug"
)

//load nms
file(core).listFiles()?.filter {
    it.isDirectory && it.name.startsWith("v")
}?.forEach { file ->
    include(":$core:${file.name}")
}