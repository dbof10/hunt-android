Hunt
=======

https://medium.com/@Cuong.Le/open-sourcing-hunt-d49919960ef1

## Features

- Product-Hunt-like features
- Infinite scrolling on supported services
- Pleasant, simple, consistent UI for across services
- Smart deeplinking into the app

## Technologies

- Kotlin
- RxJava 2/AutoDispose
- Dagger 2
- Glide
- Apollo GraphQL
- React Native
- Litho

## Project structure

- GraphQL:
    - Schema: `src/main/graphql/schema.json`
    - Query: `src/main/graphql`
- React Native:
    - Bundle: `src/main/assets`
    - JS: `src/main/js`

To play with React Native:
   - In JS directory
        `npm install` first

## Influences

This app owes a lot of its inspiration, implementation details, and general inner workings to the
work of others. Particularly:
- [Nick Butcher](https://twitter.com/@crafty) and his [Plaid](https://github.com/nickbutcher/plaid) app

## Download

<a href='https://play.google.com/store/apps/details?id=com.ctech.eaty'>
    <img alt='Get it on Google Play'
         src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'
         height="116" width="300"/>
</a>

## Contributing
We intend for this project to be an educational resource: we are excited to share our wins, mistakes, and methodology of Android development as we work in the open. Our primary focus is to continue improving the app for our users in line with our roadmap.

The best way to submit feedback and report bugs is to open a GitHub issue. Please be sure to include your operating system, device, version number, and steps to reproduce reported bugs. Keep in mind that all participants will be expected to follow our code of conduct.

## Code of Conduct
We aim to share our knowledge and findings as we work daily to improve our product, for our community, in a safe and open space. We work as we live, as kind and considerate human beings who learn and grow from giving and receiving positive, constructive feedback. We reserve the right to delete or ban any behavior violating this base foundation of respect.

## Want to financial support?

BTC wallet **1HX42mCUEPui3MJHGiJYfq2B2Gb1Y8DBdb**

ETH wallet **0x26A08128e34F7c9cb783a41E76EDd5D6fD036b07**

License
-------

    Copyright (C) 2018 Daniel Lee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
