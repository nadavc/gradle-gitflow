package com.github.amkay.gradle.gitflow.strategy

import com.github.amkay.gradle.gitflow.dsl.GitflowPluginExtension
import com.github.amkay.gradle.gitflow.util.NearestVersionLocator
import com.github.amkay.gradle.gitflow.util.VersionBuilder
import com.github.zafarkhaja.semver.Version
import org.ajoberstar.grgit.Grgit

/**
 * TODO
 *
 * @author Max Kaeufer
 */
public class DetachedHeadStrategy extends Strategy {

    @Override
    protected Version doInfer(final Grgit grgit, final GitflowPluginExtension ext) {
        def nearestVersion = new NearestVersionLocator().locate(grgit)

        new VersionBuilder(nearestVersion)
          .branch(ext.preReleaseIds.detachedHead)
          .distanceFromRelease()
          .sha(grgit, ext)
          .dirty(grgit, ext)
          .build()
    }

    @Override
    public boolean canInfer(final Grgit grgit) {
        // grgit.branch.current should be used, but that does never return null (not even when on detached head)
        true
    }

}