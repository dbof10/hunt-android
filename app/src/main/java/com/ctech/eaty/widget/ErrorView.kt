package com.ctech.eaty.widget;

class ErrorView()  {
//     val ALIGNMENT_LEFT = 0
//     val ALIGNMENT_CENTER = 1
//     val ALIGNMENT_RIGHT = 2
//
//    private lateinit var mErrorImageView: ImageView
//    private lateinit var mTitleTextView: TextView
//    private lateinit var mSubtitleTextView: TextView
//    private lateinit var mRetryButton: TextView
//
//    private var mListener: RetryListener? = null
//
//     constructor(context: Context) : this(context, null)
//
//
//    constructor(context: Context,  attrs: AttributeSet?): this(context, attrs, R.attr.style)
//
//
//    constructor(context: Context,  attrs: AttributeSet?, defStyle : Int): this(context, attrs, defStyle, 0)
//
//
//    constructor(context: Context,  attrs: AttributeSet?, defStyle : Int, defStyleRes: Int): super(context, attrs) {
//        init(attrs, defStyle, defStyleRes)
//    }
//
//
//    private fun init( attrs: AttributeSet?,  defStyle: Int, defStyleRes: Int) {
//        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyle, defStyleRes);
//
//        val inflater = context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        inflater.inflate(R.layout.view_error, this, true);
//        orientation = VERTICAL;
//        gravity = Gravity.CENTER;
//
//        /* Set android:animateLayoutChanges="true" programmatically */
//        layoutTransition = LayoutTransition()
//
//        ButterKnife.bind(this)
//
//        val imageRes
//
//        val title
//        val titleColor
//
//        val subtitle
//        val subtitleColor
//
//        val showTitle;
//        val showSubtitle;
//        val showRetryButton;
//
//        val retryButtonText;
//        val retryButtonBackground;
//        val retryButtonTextColor;
//
//        try {
//            imageRes = a.getResourceId(R.styleable.ErrorView_errorImage, 0);
//            title = a.getString(R.styleable.ErrorView_title);
//            titleColor = a.getColor(R.styleable.ErrorView_titleColor,
//                    resources.getColor(R.color.error_view_text));
//            subtitle = a.getString(R.styleable.ErrorView_subtitle);
//            subtitleColor = a.getColor(R.styleable.ErrorView_subtitleColor,
//                    resources.getColor(R.color.error_view_text_light));
//            showTitle = a.getBoolean(R.styleable.ErrorView_showTitle, true);
//            showSubtitle = a.getBoolean(R.styleable.ErrorView_showSubtitle, true);
//            showRetryButton = a.getBoolean(R.styleable.ErrorView_showRetryButton, true);
//            retryButtonText = a.getString(R.styleable.ErrorView_retryButtonText);
//            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_retryButtonBackground, 0);
//            retryButtonTextColor = a.getColor(R.styleable.ErrorView_retryButtonTextColor,
//                    resources.getColor(R.color.error_view_text_dark));
//            val alignInt = a.getInt(R.styleable.ErrorView_subtitleAlignment, 1);
//
//            if (imageRes != 0) {
//                setImage(imageRes);
//            }
//
//            if (title != null) {
//                setTitle(title);
//            }
//
//            if (subtitle != null) {
//                setSubtitle(subtitle);
//            }
//
//            if (retryButtonText != null) {
//                mRetryButton.setText(retryButtonText);
//            }
//
//            if (!showTitle) {
//                mTitleTextView.setVisibility(GONE);
//            }
//
//            if (!showSubtitle) {
//                mSubtitleTextView.setVisibility(GONE);
//            }
//
//            if (!showRetryButton) {
//                mRetryButton.setVisibility(GONE);
//            }
//
//            mTitleTextView.setTextColor(titleColor);
//            mSubtitleTextView.setTextColor(subtitleColor);
//
//            mRetryButton.setTextColor(retryButtonTextColor);
//
//            if(retryButtonBackground != 0)
//            mRetryButton.setBackgroundResource(retryButtonBackground);
//
//            setSubtitleAlignment(alignInt);
//        } finally {
//            a.recycle();
//        }
//
//        mRetryButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mListener != null) {
//                    mListener.onRetry();
//                }
//            }
//        });
//    }
//
//     fun setOnRetryListener( listener: RetryListener) {
//        mListener = listener;
//    }
//
//
//    /**
//     * Loads ErrorView configuration from a {@link Config} object.
//     *
//     * @param config configuration to load from
//     */
//    fun setConfig(Config config) {
//        if (config.getImage() != null) {
//            Object image = config.getImage();
//            if (image instanceof Integer) {
//                setImage((int) image);
//            } else if (image instanceof Drawable) {
//                setImage((Drawable) image);
//            } else if (image instanceof Bitmap) {
//                setImage((Bitmap) image);
//            }
//        }
//
//        if (config.getTitle() != null) {
//            setTitle(config.getTitle());
//        }
//
//        if (config.getTitleColor() != 0) {
//            setTitleColor(config.getTitleColor());
//        }
//
//        if (config.getSubtitle() != null) {
//            setSubtitle(config.getSubtitle());
//        }
//
//        if (config.getSubtitleColor() != 0) {
//            setSubtitleColor(config.getSubtitleColor());
//        }
//
//        showRetryButton(config.shouldShowRetryButton());
//
//        if (config.getRetryButtonText() != null) {
//            setRetryButtonText(config.getRetryButtonText());
//        }
//
//        if (config.getRetryButtonTextColor() != 0) {
//            setRetryButtonTextColor(config.getRetryButtonTextColor());
//        }
//    }
//
//    /**
//     * Returns the current ErrorView configuration.
//     */
//    public Config getConfig() {
//        return Config.create()
//                .image(getImage())
//                .title(getTitle())
//                .titleColor(getTitleColor())
//                .subtitle(getSubtitle())
//                .subtitleColor(getSubtitleColor())
//                .retryVisible(isRetryButtonVisible())
//                .retryText(getRetryButtonText())
//                .retryTextColor(getRetryButtonTextColor())
//                .build();
//    }
//
//
//    /**
//     * Sets error image to a given drawable resource
//     *
//     * @param res drawable resource.
//     */
//    fun setImage(int res) {
//        mErrorImageView.setImageResource(res);
//    }
//
//    /**
//     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
//     *
//     * @param drawable {@link android.graphics.drawable.Drawable} to use as error image.
//     */
//    fun setImage(Drawable drawable) {
//        mErrorImageView.setImageDrawable(drawable);
//    }
//
//    /**
//     * Sets the error image to a given {@link android.graphics.Bitmap}.
//     *
//     * @param bitmap {@link android.graphics.Bitmap} to use as error image.
//     */
//    fun setImage(Bitmap bitmap) {
//        mErrorImageView.setImageBitmap(bitmap);
//    }
//
//    /**
//     * Returns the current error iamge
//     */
//    public Drawable getImage() {
//        return mErrorImageView.getDrawable();
//    }
//
//    /**
//     * Sets the error title to a given {@link java.lang.String}.
//     *
//     * @param text {@link java.lang.String} to use as error title.
//     */
//    fun setTitle(String text) {
//        mTitleTextView.setText(text);
//    }
//
//    /**
//     * Sets the error title to a given string resource.
//     *
//     * @param res string resource to use as error title.
//     */
//    fun setTitle(int res) {
//        mTitleTextView.setText(res);
//    }
//
//    /**
//     * Returns the current title string.
//     */
//    public String getTitle() {
//        return mTitleTextView.getText().toString();
//    }
//
//    /**
//     * Sets the error title text to a given color.
//     *
//     * @param res color resource to use for error title text.
//     */
//    fun setTitleColor(int res) {
//        mTitleTextView.setTextColor(res);
//    }
//
//    /**
//     * Returns the current title text color.
//     */
//    public int getTitleColor() {
//        return mTitleTextView.getCurrentTextColor();
//    }
//
//    /**
//     * Sets the error subtitle to a given {@link java.lang.String}.
//     *
//     * @param exception {@link java.lang.String} to use as error subtitle.
//     */
//    fun setSubtitle(String exception) {
//        mSubtitleTextView.setText(exception);
//    }
//
//    /**
//     * Sets the error subtitle to a given string resource.
//     *
//     * @param res string resource to use as error subtitle.
//     */
//    fun setSubtitle(int res) {
//        mSubtitleTextView.setText(res);
//    }
//
//    /**
//     * Returns the current subtitle.
//     */
//    public String getSubtitle() {
//        return mSubtitleTextView.getText().toString();
//    }
//
//    /**
//     * Sets the error subtitle text to a given color
//     *
//     * @param res color resource to use for error subtitle text.
//     */
//    fun setSubtitleColor(int res) {
//        mSubtitleTextView.setTextColor(res);
//    }
//
//    /**
//     * Returns the current subtitle text color.
//     */
//    public int getSubtitleColor() {
//        return mSubtitleTextView.getCurrentTextColor();
//    }
//
//    /**
//     * Sets the retry button's text to a given {@link java.lang.String}.
//     *
//     * @param text {@link java.lang.String} to use as retry button text.
//     */
//    fun setRetryButtonText(String text) {
//        mRetryButton.setText(text);
//    }
//
//    /**
//     * Sets the retry button's text to a given string resource.
//     *
//     * @param res string resource to be used as retry button text.
//     */
//    fun setRetryButtonText(int res) {
//        mRetryButton.setText(res);
//    }
//
//    /**
//     * Returns the current retry button text.
//     */
//    public String getRetryButtonText() {
//        return mRetryButton.getText().toString();
//    }
//
//    /**
//     * Sets the retry button's text color to a given color.
//     *
//     * @param color int color to be used as text color.
//     */
//    fun setRetryButtonTextColor(int color) {
//        mRetryButton.setTextColor(color);
//    }
//
//    /**
//     * Returns the current retry button text color.
//     */
//    public int getRetryButtonTextColor() {
//        return mRetryButton.getCurrentTextColor();
//    }
//
//    /**
//     * Shows or hides the error title
//     */
//    public void showTitle(boolean show) {
//        mTitleTextView.setVisibility(show ? VISIBLE : GONE);
//    }
//
//    /**
//     * Indicates whether the title is currently visible.
//     */
//    public boolean isTitleVisible() {
//        return mTitleTextView.getVisibility() == VISIBLE;
//    }
//
//    /**
//     * Shows or hides error subtitle.
//     */
//    public void showSubtitle(boolean show) {
//        mSubtitleTextView.setVisibility(show ? VISIBLE : GONE);
//    }
//
//    /**
//     * Indicates whether the subtitle is currently visible.
//     */
//    public boolean isSubtitleVisible() {
//        return mSubtitleTextView.getVisibility() == VISIBLE;
//    }
//
//    /**
//     * Shows or hides the retry button.
//     */
//    public void showRetryButton(boolean show) {
//        mRetryButton.setVisibility(show ? VISIBLE : GONE);
//    }
//
//    /**
//     * Indicates whether the retry button is visible.
//     */
//    public boolean isRetryButtonVisible() {
//        return mRetryButton.getVisibility() == VISIBLE;
//    }
//
//    /**
//     * Sets the subtitle's alignment to a given value
//     */
//    public void setSubtitleAlignment(int alignment) {
//        if (alignment == ALIGNMENT_LEFT) {
//            mSubtitleTextView.setGravity(Gravity.LEFT);
//        } else if (alignment == ALIGNMENT_CENTER) {
//            mSubtitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
//        } else {
//            mSubtitleTextView.setGravity(Gravity.RIGHT);
//        }
//    }
//
//    /**
//     * Returns the current subtitle allignment
//     */
//    public int getSubtitleAlignment() {
//        int gravity = mSubtitleTextView.getGravity();
//        if (gravity == Gravity.LEFT) {
//            return ALIGNMENT_LEFT;
//        } else if (gravity == Gravity.CENTER_HORIZONTAL) {
//            return ALIGNMENT_CENTER;
//        } else {
//            return ALIGNMENT_RIGHT;
//        }
//    }
//
//    public static class Config {
//        private Object mImage;
//        private String mTitle;
//        private int mTitleColor;
//        private String mSubtitle;
//        private int mSubtitleColor;
//        private boolean mShowRetryButton = true;
//        private String mRetryButtonText;
//        private int mRetryButtonTextColor;
//
//        public static Builder create() {
//            return new Builder();
//        }
//
//        private Config() {
//            /* epmty */
//        }
//
//        public Object getImage() {
//            return mImage;
//        }
//
//        public String getTitle() {
//            return mTitle;
//        }
//
//        public int getTitleColor() {
//            return mTitleColor;
//        }
//
//        public String getSubtitle() {
//            return mSubtitle;
//        }
//
//        public int getSubtitleColor() {
//            return mSubtitleColor;
//        }
//
//        public boolean shouldShowRetryButton() {
//            return mShowRetryButton;
//        }
//
//        public String getRetryButtonText() {
//            return mRetryButtonText;
//        }
//
//        public int getRetryButtonTextColor() {
//            return mRetryButtonTextColor;
//        }
//
//        public static class Builder {
//            private Config config;
//
//            private Builder() {
//                config = new Config();
//            }
//
//            public Builder image(int res) {
//                config.mImage = res;
//                return this;
//            }
//
//            public Builder image(Drawable drawable) {
//                config.mImage = drawable;
//                return this;
//            }
//
//            public Builder image(Bitmap bitmap) {
//                config.mImage = bitmap;
//                return this;
//            }
//
//            public Builder title(String title) {
//                config.mTitle = title;
//                return this;
//            }
//
//            public Builder titleColor(int color) {
//                config.mTitleColor = color;
//                return this;
//            }
//
//            public Builder subtitle(String subtitle) {
//                config.mSubtitle = subtitle;
//                return this;
//            }
//
//            public Builder subtitleColor(int color) {
//                config.mSubtitleColor = color;
//                return this;
//            }
//
//            public Builder retryVisible(boolean visible) {
//                config.mShowRetryButton = visible;
//                return this;
//            }
//
//            public Builder retryText(String text) {
//                config.mRetryButtonText = text;
//                return this;
//            }
//
//            public Builder retryTextColor(int color) {
//                config.mRetryButtonTextColor = color;
//                return this;
//            }
//
//            public Config build() {
//                return config;
//            }
//        }
//    }
//
//    public interface RetryListener {
//        void onRetry();
//    }
}