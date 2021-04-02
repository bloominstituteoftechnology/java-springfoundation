package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
    public class UserProductId implements Serializable {

        private long user;

        private long product;




        public long getUser() {
            return user;
        }

        public void setUser(long user) {
            this.user = user;
        }

        public long getProduct() {
            return product;
        }

        public void setProduct(long product) {
            this.product = product;
        }

        @Override
        public boolean equals(Object o)
        {
            if(this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            UserProductId that = (UserProductId) o;
            return this.user == that.user &&
                    this.product== that.product;








        }

        @Override
        public int hashCode() {
            return 37;
        }

    }
