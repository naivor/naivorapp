/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.presentation.widget.imagepicker;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianlai on 16-4-12.
 */
public class ImageCollection {
    private String rootPath;
    private String rootName;
    private Set<String> pictures;

    public ImageCollection() {
    }

    public ImageCollection(Set<String> pictures, String rootName) {
        this.pictures = pictures;
        this.rootName = rootName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(String picture){
        if (pictures==null){
            pictures=new HashSet<>();
        }

        pictures.add(picture);
    }

    public void rmPicture(String picture){
        if (pictures==null){
            return;
        }

        pictures.remove(picture);
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public String toString() {
        return "ImageCollection{" +
                "rootPath='" + rootPath + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
