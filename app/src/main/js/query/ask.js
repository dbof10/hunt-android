
export const QUERY_ASK_CONST = `query ProductRequestsPage($cursor: String, $productRequestFilter: ProductRequestFilter) {
    product_requests(first: 20, filter: $productRequestFilter, after: $cursor) {
    edges {        
            node {
                  _id         
                  id         
                  ...ProductRequestItem        
                        
                  }       
                      
         }     
            pageInfo {       
                            endCursor       
                            hasNextPage       
                                }    
                              
                    }  
                }  
   
               fragment UserImage on User {   
                                     id
                                     post_upvote_streak   
                                     
                                     } 
               fragment ProductRequestItem on ProductRequest {   
                                     _id   
                                     id   
                                     body_html   
                                     created_at   
                                     is_advice   
                                     recommended_products_count   
                                     title   
                                     user {     
                                                    _id     
                                                    id     
                                                    ...UserSpotlight     
                                                      
                                                    }   
                                                    ... on Commentable { 
                                                                        _id    
                                                                        id     
                                                                        comments_count     
                                                                          
                                                                        }   
                                                    ...ProductRequestFollowButton   
                                                    ...ProductRequestMenuButton   
                                                   
                                                    }
      
               fragment UserSpotlight on User {   
                                    _id   
                                    id
                                    headline
                                    name
                                    username
                                    ...UserImageLink   
                                    
                           } 
               fragment UserImageLink on User {   
                                    id
                                    _id
                                    username 
                                    ...UserImage
                                    
                           } 
               fragment ProductRequestFollowButton on ProductRequest {
                                    _id
                                    id 
                                    has_followed
                                    
                           } 
               fragment ProductRequestMenuButton on ProductRequest {
                                    _id
                                    id 
               }`;

export const QUERY_ASK_DETAIL_CONST =
    `query ProductRequestPage($id: ID!, $recommendationCursor: String, $recommendationLimit: Int, $recommendedProductCursor: String, $threadCursor: String, $threadLimit: Int!) {
  node(id: $id) {
    ... on ProductRequest {
      _id
      id
      is_advice
      ...ProductRequestAdviceContentProductRequest
      ...ProductRequestCard
      ...ProductRequestProductRequestContentProductRequest
    }
  }
  viewer {
    id
    ...AskLayoutViewer
    ...ProductRequestAdviceContentViewer
    ...ProductRequestProductRequestContentViewer
  }
}

fragment ProductRequestAdviceContentProductRequest on ProductRequest {
  _id
  id
  ... on Commentable {
    _id
    id
    can_comment
  }
  ...CommentList
}

fragment ProductRequestAdviceContentViewer on Viewer {
  id
}

fragment CommentList on Commentable {
  _id
  id
  threads(first: $threadLimit, after: $threadCursor) {
    edges {
      node {
        _id
        id
        ...CommentListThread
      }
    }
    pageInfo {
      endCursor
      hasNextPage
    }
  }
}

fragment CommentListThread on Comment {
  _id
  id
  is_sticky
  replies(first: 1) {
    edges {
      node {
        _id
        id
        ...CommentListThreadComment
      }
    }
  }
  ...CommentListThreadComment
}

fragment CommentListThreadComment on Comment {
  _id
  id
  body
  can_edit
  can_reply
  created_at
  user {
    _id
    id
    headline
    name
    username
    hiring_for_products
    ...UserSpotlight
  }
  ...CommentListThreadCommentDestroyButton
  ...CommentListThreadCommentVoteButton
  ...CommentListThreadPostNoticeComment
}

fragment CommentListThreadCommentDestroyButton on Comment {
  _id
  id
  can_destroy
}

fragment CommentListThreadCommentVoteButton on Comment {
  _id
  id
  ... on Votable {
    _id
    id
    has_voted
    votes_count
  }
}

fragment CommentListThreadPostNoticeComment on Comment {
  _id
  id
  user {
    _id
    id
  }
}

fragment UserSpotlight on User {
  _id
  id
  headline
  name
  username
  ...UserImageLink
}

fragment UserImageLink on User {
  id
  _id
  username
  ...UserImage
}

fragment UserImage on User {
  id
  post_upvote_streak
}

fragment ProductRequestCard on ProductRequest {
  _id
  id
  created_at
  featured_at
  ... on Commentable {
    _id
    id
    comments_count
  }
  ...ProductRequestCardComments
  ...ProductRequestContent
  ...ProductRequestFollowButton
  ...ProductRequestMenuButton
}

fragment ProductRequestCardComments on ProductRequest {
  _id
  id
  ... on Commentable {
    _id
    id
    can_comment
  }
  ...CommentList
}

fragment ProductRequestContent on ProductRequest {
  _id
  id
  body_html
  title
  user {
    _id
    id
    ...UserSpotlight
  }
}

fragment ProductRequestFollowButton on ProductRequest {
  _id
  id
  has_followed
}

fragment ProductRequestMenuButton on ProductRequest {
  _id
  id
  can_destroy
  can_edit
}

fragment ProductRequestProductRequestContentProductRequest on ProductRequest {
  _id
  id
  can_recommend
  recommended_products_count
  ...RecommendedProductList
}

fragment ProductRequestProductRequestContentViewer on Viewer {
  id
}

fragment RecommendedProductList on ProductRequest {
  _id
  id
  recommended_products(first: 20, after: $recommendedProductCursor, order: VOTES) {
    edges {
      node {
        _id
        id
        ...RecommendedProductListItem
      }
    }
    pageInfo {
      endCursor
      hasNextPage
    }
  }
}

fragment RecommendedProductListItem on RecommendedProduct {
  _id
  id
  product_request {
    _id
    id
    can_recommend
  }
  recommendations(first: $recommendationLimit, after: $recommendationCursor, order: VOTES) {
    edges {
      node {
        _id
        id
        ...RecommendationCard
      }
    }
    pageInfo {
      endCursor
      hasNextPage
    }
  }
  ...RecommendedProductCard
  ...RecommendedProductListItemRecommendationForm
}

fragment RecommendedProductListItemRecommendationForm on RecommendedProduct {
  _id
  id
  product {
    id
  }
  product_request {
    _id
    id
  }
}

fragment RecommendationCard on Recommendation {
  _id
  id
  body_html
  can_edit
  created_at
  edited_at
  is_disclosed
  is_highlighted
  user {
    _id
    id
    helpful_recommendations_count
    name
    ...UserSpotlight
  }
  ... on Commentable {
    _id
    id
    comments_count
  }
  ...RecommendationCardDestroyButton
  ...RecommendationCardMigrateButton
  ...RecommendationCardVoteButton
}

fragment RecommendationCardDestroyButton on Recommendation {
  _id
  id
  can_destroy
  recommended_product {
    _id
    id
    product_request {
      _id
      id
    }
  }
}

fragment RecommendationCardMigrateButton on Recommendation {
  _id
  id
  recommended_product {
    _id
    id
    product_request {
      _id
      id
    }
  }
}

fragment RecommendationCardVoteButton on Recommendation {
  _id
  id
  ... on Votable {
    _id
    id
    has_voted
    votes_count
  }
}

fragment RecommendedProductCard on RecommendedProduct {
  _id
  id
  name
  product {
    id
    posts(first: 1, filter: FEATURED) {
      edges {
        node {
          _id
          id
          name
          tagline
        }
      }
    }
    primary_link {
      id
      redirect_path
    }
    thumbnail_media {
      id
      ...MediaThumbnail
    }
  }
  ...RecommendedProductCardMergeForm
  ...RecommendedProductVoteButton
}

fragment RecommendedProductCardMergeForm on RecommendedProduct {
  _id
  id
}

fragment MediaThumbnail on Media {
  id
  image_uuid
}

fragment RecommendedProductVoteButton on RecommendedProduct {
  _id
  id
  ... on Votable {
    _id
    id
    has_voted
    votes_count
  }
}


fragment AskLayoutViewer on Viewer {
  id
  ...AskLayoutBreadcrumbViewer
}

fragment AskLayoutBreadcrumbViewer on Viewer {
  id
  can_request_product
}`;