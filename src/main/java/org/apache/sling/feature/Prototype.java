/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.sling.feature;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A prototype is a blueprint of a feature with optional removals of
 * <ul>
 * <li>Configurations / configuration properties
 * <li>Bundles
 * <li>Framework properties
 * <li>Extensions or artifacts from extensions
 * </ul>
 *
 * This class is not thread-safe.
 *
 * TODO - requirement, capabilities
 *
 */
public class Prototype implements Comparable<Prototype>, Cloneable {

    private final ArtifactId id;
    private final URL url;

    private final List<String> configurationRemovals = new ArrayList<>();

    private final List<ArtifactId> bundleRemovals = new ArrayList<>();

    private final List<String> frameworkPropertiesRemovals = new ArrayList<>();

    private final List<String> extensionRemovals = new ArrayList<>();

    private final Map<String, List<ArtifactId>> artifactExtensionRemovals = new HashMap<>();

    /**
     * Construct a new Include.
     * @param id The id of the feature.
     * @throws IllegalArgumentException If id is {@code null}.
     */
    public Prototype(final ArtifactId id) {
    	this(Objects.requireNonNull(id), null);
    }
    
    public Prototype(final URL url) {
    	this(null, Objects.requireNonNull(url));
    }
    
    private Prototype(final ArtifactId id, final URL url) {
        this.id = id;
        this.url = url;
    }
    
    @Override
    public Prototype clone() {
    	return new Prototype(id, url);
    }

    /**
     * Get the id of the artifact.
     * @return The id. May be null.
     */
    public ArtifactId getId() {
        return this.id;
    }
    
    public URL getUrl() {
		return url;
	}

    public List<String> getConfigurationRemovals() {
        return configurationRemovals;
    }

    public List<ArtifactId> getBundleRemovals() {
        return bundleRemovals;
    }

    public List<String> getFrameworkPropertiesRemovals() {
        return frameworkPropertiesRemovals;
    }

    public List<String> getExtensionRemovals() {
        return extensionRemovals;
    }

    public Map<String, List<ArtifactId>> getArtifactExtensionRemovals() {
        return artifactExtensionRemovals;
    }

    @Override
    public int compareTo(final Prototype o) {
    	if (o == null)
    		return 1;
    	final String thisUrl = id != null ? id.toMvnUrl() : url.toString();
    	final String otherUrl = o.id != null ? o.id.toMvnUrl() : url.toString();
    	return thisUrl.compareTo(otherUrl);
    }

    @Override
    public int hashCode() {
        return id != null ? this.id.hashCode() : url.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Prototype other = (Prototype) obj;
        if (id == null && other.id != null)
        	return false;
        if (id != null && other.id == null)
        	return false;
        return id != null ? this.id.equals(other.id) : this.url.equals(other.url);
    }

    @Override
    public String toString() {
        return "Include [id=" + (id != null ? id.toMvnId() : url.toString()) + "]";
    }
}
