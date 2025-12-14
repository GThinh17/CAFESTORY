"use client";

import { useEffect, useState } from "react";
import "./goProModal.scss";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

interface PricingPlansProps {
  open: boolean;
  onClose: () => void;
}

export function PricingPlans({ open, onClose }: PricingPlansProps) {
  const [plans, setPlans] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const { user, token } = useAuth(); // ⚡ Lấy userId từ context
  const [isCfOwner, setIsCfOwner] = useState(false);
  const [isReviewer, setIsReviewer] = useState(false);

  useEffect(() => {
    const fetchstatus = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}/exists`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        setIsCfOwner(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchstatus();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchstatus = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/reviewers/${user?.id}/exists`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setIsReviewer(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchstatus();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/production");
        setPlans(res.data.data || []);
      } catch (err) {
        console.error("Failed to fetch production plans:", err);
      } finally {
        setLoading(false);
      }
    };

    if (open) fetchPlans();
  }, [open]);

  // ⚡ Hàm gọi API tạo checkout session
  const handleStartSubscription = async (productionId: string) => {
    if (!user?.id) {
      alert("Bạn cần đăng nhập trước!");
      return;
    }

    try {
      const body = {
        userId: user.id,
        productionId: productionId,
        paymentMethodId: "30a80395-ee12-417d-ad6f-1a96f21637de",
        amount: "1",
      };

      const res = await axios.post(
        "http://localhost:8080/api/create-checkout-session",
        body,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log(res);
      const redirectUrl = res.data.data?.url;

      if (redirectUrl) {
        window.location.href = redirectUrl; // ⚡ Redirect sang Stripe
      } else {
        console.error("No URL returned from backend");
      }
    } catch (err) {
      console.error("Failed to create checkout session:", err);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="pricingModal">
        <DialogHeader>
          <DialogTitle className="pricingModal__title">
            Upgrade your plan
          </DialogTitle>
          <p className="pricingModal__subtitle">
            Choose a plan to unlock premium features.
          </p>
        </DialogHeader>

        {loading ? (
          <p className="text-center py-6">Loading...</p>
        ) : (
          <div className="pricingModal__cards">
            {plans
              .filter((plan) => {
                if (isReviewer && plan.productionType === "CAFEOWNER")
                  return false;
                if (isCfOwner && plan.productionType === "REVIEWER")
                  return false;
                return true;
              })
              .map((plan) => {
                const isOwned =
                  (isReviewer && plan.productionType === "REVIEWER") ||
                  (isCfOwner && plan.productionType === "CAFEOWNER");

                return (
                  <Card key={plan.productionId} className="pricingCard">
                    <CardHeader>
                      <CardTitle className="pricingCard__name">
                        {plan.productionType}
                      </CardTitle>
                      <p className="pricingCard__desc">{plan.description}</p>
                    </CardHeader>

                    <CardContent>
                      <div className="pricingCard__price">
                        ${plan.total}
                        <span className="pricingCard__usd"> USD</span>
                      </div>

                      <p className="pricingCard__per">
                        {plan.timeExpired} months subscription
                      </p>

                      {/* ⭐ BUTTON CHUYỂN "RENEW SUBSCRIPTION" NẾU ĐÃ CÓ GÓI */}
                      <Button
                        className="pricingCard__btn"
                        onClick={() =>
                          handleStartSubscription(plan.productionId)
                        }
                      >
                        {isOwned
                          ? "Renew subscription"
                          : "Start this subscription"}
                      </Button>

                      <div>
                        <p className="pricingCard__section">
                          Plan information:
                        </p>
                        <ul className="pricingCard__features">
                          <li className="pricingCard__feature active">
                            • Type: {plan.productionType}
                          </li>
                          <li className="pricingCard__feature active">
                            • Status: {plan.status}
                          </li>
                          <li className="pricingCard__feature active">
                            • Created:{" "}
                            {new Date(plan.createdAt).toLocaleString()}
                          </li>
                        </ul>
                      </div>
                    </CardContent>
                  </Card>
                );
              })}
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
}
