package io.justdevit.kotlin.boost.kotest.logging

import io.justdevit.kotlin.boost.kotest.install
import io.justdevit.kotlin.boost.kotest.logging.assertion.assertLogs
import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.assertThrows

class AssertLogsTest :
    FreeSpec(
        {

            install {
                logging()
            }

            "Simple level detection tests" -
                {

                    "Should be able to detect simple message" {
                        println("Hello")

                        assertLogs {
                            message("Hello")
                        }
                    }

                    "Should be able to detect TRACE" {
                        println("TRACE Hello")

                        assertLogs {
                            trace("Hello")
                        }
                    }

                    "Should be able to detect DEBUG" {
                        println("DEBUG Hello")

                        assertLogs {
                            debug("Hello")
                        }
                    }

                    "Should be able to detect INFO" {
                        println("INFO Hello")

                        assertLogs {
                            info("Hello")
                        }
                    }

                    "Should be able to detect WARN" {
                        println("WARN Hello")

                        assertLogs {
                            warn("Hello")
                        }
                    }

                    "Should be able to detect ERROR" {
                        println("ERROR Hello")

                        assertLogs {
                            error("Hello")
                        }
                    }
                }

            "Log Level detection tests" -
                {
                    listOf(
                        "INFO Hello",
                        "2026-01-01 INFO Hello",
                        "[INFO] Hello",
                        "(INFO) Hello",
                        "INFO: Hello",
                        """{"level":"INFO","message":"Hello"}""",
                    ).forEach {
                        "Should detect log level from | $it" {
                            println(it)

                            assertLogs {
                                info("Hello")
                            }
                        }
                    }
                }

            "Message detection tests" -
                {
                    "Should be able to detect simple message" {
                        println("Hello World")

                        assertLogs {
                            message("Hello")
                            message("World")
                        }
                    }

                    "Should be able to detect message missing" {
                        println("Hello World")

                        assertLogs {
                            message(!"TEST")
                            !message("TEST")
                        }
                    }

                    "Should be able to detect message with regex" {
                        println("2026-01-01 Testing")

                        assertLogs {
                            message("2026-.*".toRegex())
                            message(!"2025-.*".toRegex())
                            !message("2025-.*".toRegex())
                        }
                    }
                }

            "Message occurrence tests" -
                {
                    "Should be able to detect message occurrence" {
                        println("Hello")
                        println("Hello")

                        assertLogs {
                            !message("Hello", exactly = 1)
                            message("Hello", exactly = 2)
                            !message("Hello", exactly = 3)
                        }
                    }
                }

            "Message order tests" -
                {
                    "Should be able to detect message order" {
                        println("Hello")
                        println("World")

                        assertLogs(ordered = true) {
                            message("Hello")
                            message("World")
                        }
                    }

                    "Should reject incorrect order" {
                        println("Hello")
                        println("World")

                        assertThrows<AssertionError> {
                            assertLogs(ordered = true) {
                                message("World")
                                message("Hello")
                            }
                        }
                    }

                    "Should be able to accept messages unorderly" {
                        println("Hello")
                        println("World")

                        assertLogs {
                            message("Hello")
                            message("World")
                        }
                    }
                }

            "Scenario tests" -
                {
                    "Scenario 1" {
                        println("INFO: Application starting...")
                        println("INFO: Application started.")

                        println("INFO: Request [123] has accepted.")
                        println("DEBUG: Processing request [123]...")
                        println("DEBUG: Sending event for request [123]...")
                        println("WARN: Sending event for request [123] has failed. Retry: 1.")
                        println("WARN: Sending event for request [123] has failed. Retry: 2.")
                        println("WARN: Sending event for request [123] has failed. Retry: 3.")
                        println("ERROR: Sending event for request [123] has failed.")
                        println("INFO: Request [123] has finished. Status: FAILED.")

                        println("INFO: Application finishing...")
                        println("INFO: Application finished.")

                        assertLogs {
                            message("[123]", exactly = 8)
                            !debug("Sending event for request \\[123] has failed\\. Retry: [123]\\.".toRegex(), exactly = 3)
                        }
                        assertLogs(ordered = true) {
                            info("Application starting...", exactly = 1)
                            info("Application started.", exactly = 1)

                            info("Request [123] has accepted.", exactly = 1)
                            debug("Processing request [123]...", exactly = 1)
                            debug("Sending event for request [123]...", exactly = 1)
                            warn("Sending event for request \\[123] has failed\\. Retry: [123]\\.".toRegex(), exactly = 3)
                            error("Sending event for request [123] has failed.", exactly = 1)
                            info("Request [123] has finished. Status: FAILED.", exactly = 1)

                            info("Application finishing...", exactly = 1)
                            info("Application finished.", exactly = 1)
                        }
                    }
                }
        },
    )
