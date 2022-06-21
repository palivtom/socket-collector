import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.google.protobuf") version "0.8.18"

	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "cz.palivtom"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// logging
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

	// jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// protobuf
	implementation("com.google.protobuf:protobuf-java:3.21.1")

	// grpc
	implementation("io.grpc:grpc-protobuf:1.47.0")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.21.1"
	}

	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.47.0"
		}
	}

	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				id("grpc")
			}
		}
	}
}

sourceSets {
	main {
		proto {
			srcDir("proto")
			include("./**/*.proto")
		}
	}
}

tasks.jar {
	enabled = false
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
