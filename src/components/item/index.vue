<template>
    <div class="cakes">
        <img
                :src="imagePath"
                :alt="$t(attibutes.title)"
                class="cakes-image"
        />
        <p class="cakes-text">{{ $t(attibutes.title) }}</p>
        <div class="cakes-price">
            <div class="price-text">￥{{attibutes.price}}</div>
            <div class="button-tick" >
                <div class="price-tick" v-if="this.hasItemInCart(attibutes.id)">
                    <img alt="Tick" src="@/assets/images/Tick.png"/>
                </div>
                <div class="price-button" v-else>
                    <button class="price-submit" @click="addToCart(attibutes)">
                        <img alt="Cart button" src="@/assets/images/buttonCart.png"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>


<script lang="ts">


    import {Component, Vue, Prop} from "vue-property-decorator";
    import {iItem} from "@/common/interface/item";
    import {Getter, namespace, Mutation} from "vuex-class";
    const CartGetter = namespace('cart', Getter)
    const CartMutation = namespace('cart',Mutation)
    @Component
    export default class Item extends Vue {
        @Prop({
            default: null
        }) readonly attibutes!: iItem

        get imagePath(){
            return require(`@/assets/images/${this.attibutes.image}`)
        }

        @CartMutation addToCart: any

        @CartGetter hasItemInCart!: (id:number) => boolean

    }
</script>

<style lang="scss" scoped>
    @import "@/styles/variables";

    .cakes {
        @media only screen and (max-width: 1200px) {
            flex-basis: 30%;
        }
        @media only screen and (max-width: 768px) {
            flex-basis: 48%;
        }

        @media only screen and (max-width: 480px) {
            flex-basis: 100%;
        }

        &:last-of-type {
            /*margin-right: auto;*/
        }

        flex-basis: 21%;
        /*margin-right: 10px;*/
        margin-bottom: 25px;
        background-color: $white;
        padding: 3px;
        border-radius: 8px;
        box-shadow: -11px 9px 21px 0 rgba(173, 182, 217, 0.17);

        .cakes-image {
            width: 100%;
        }

        .cakes-text {
            font-size: 16px;
            color: $darkgrey;
            font-weight: bold;
            padding: 15px 0px 35px 13px;
        }

        .cakes-price {
            display: flex;
            justify-content: space-between;
            padding: 0px 13px 28px 13px;

            .price-text {
                font-size: 20px;
                font-weight: bold;
                color: $red;
            }

            .button-tick {
                display: flex;

                .price-tick {
                    padding-right: 3px;
                }

                .price-button {
                    .price-submit {
                        cursor: pointer;
                        border-radius: 4px;
                        background-color: #f7c22e;
                        box-shadow: 0 5px 8px 0 rgba(255, 208, 81, 0.5);
                        border: none;
                        padding: 4px 23px 3px 23px;
                    }
                }
            }
        }
    }
</style>