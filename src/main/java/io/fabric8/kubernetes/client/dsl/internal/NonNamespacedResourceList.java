package io.fabric8.kubernetes.client.dsl.internal;

import com.ning.http.client.AsyncHttpClient;
import io.fabric8.kubernetes.api.builder.Builder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.client.dsl.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.NamedResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NonNamespacedResourceList<T extends HasMetadata, L extends KubernetesResourceList, B extends Builder<T>>
  extends DefaultResourceList<T, L, B>
  implements io.fabric8.kubernetes.client.dsl.NonNamespacedResourceList<T, L, B> {

  public NonNamespacedResourceList(AsyncHttpClient httpClient, URL rootUrl, String resourceT, Class<T> clazz, Class<L> listClazz, Class<B> builderClazz) {
    super(httpClient, rootUrl, resourceT, clazz, listClazz, builderClazz);
  }

  @Override
  public NamedResource<T, B> withName(String name) throws KubernetesClientException {
    try {
      return new io.fabric8.kubernetes.client.dsl.internal.NamedResource<>(name, this);
    } catch (MalformedURLException e) {
      throw new KubernetesClientException("Malformed resource URL", e);
    }
  }

  @Override
  public T create(T resource) throws KubernetesClientException {
    try {
      return handleCreate(resource);
    } catch (InterruptedException | ExecutionException | IOException e) {
      throw new KubernetesClientException("Unable to create resource", e);
    }
  }

}