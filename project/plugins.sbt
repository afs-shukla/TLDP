// Comment to get more information during initialization
//logLevel := Level.Warn


//resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.0")

addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.15")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
