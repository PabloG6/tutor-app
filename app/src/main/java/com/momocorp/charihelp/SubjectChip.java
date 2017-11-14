package com.momocorp.charihelp;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;
import com.tylersuehr.chips.data.Chip;

import static com.doodle.android.chips.ChipsView.*;

/**
 * Created by OWO Technologies on 11/14/2017.
 */

public class SubjectChip extends Chip {
    private final String subjectName;
    public SubjectChip(String subjectName) {
        super();
        this.subjectName = subjectName;
    }

    @Nullable
    @Override
    public Object getId() {
        return null;
    }

    @NonNull
    @Override
    public String getTitle() {
        return subjectName;
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return null;
    }

    @Nullable
    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Nullable
    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }
}
