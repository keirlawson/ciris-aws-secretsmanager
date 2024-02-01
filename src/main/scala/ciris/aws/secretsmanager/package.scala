package ciris.aws

import cats.effect.Async
import cats.effect.kernel.{Resource, Sync}
import ciris.ConfigValue
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.secretsmanager.{
  SecretsManagerAsyncClient,
  SecretsManagerAsyncClientBuilder
}

package object secretsmanager {
  def secrets[F[_]: Async](
    region: Region
  ): ConfigValue[F, SecretString[F]] =
    secrets(
      SecretsManagerAsyncClient
        .builder()
        .region(region.asJava)
        .credentialsProvider(DefaultCredentialsProvider.create())
    )

  def secrets[F[_]: Async](
    clientBuilder: SecretsManagerAsyncClientBuilder
  ): ConfigValue[F, SecretString[F]] =
    ConfigValue.resource {
      Resource {
        Sync[F].delay {
          val client =
            clientBuilder
              .build()

          val shutdown =
            Sync[F].delay(client.close())

          (ConfigValue.default(SecretString[F](client)), shutdown)
        }
      }
    }
}
