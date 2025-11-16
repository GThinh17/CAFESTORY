"use client";

import "./goProModal.scss";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

interface PricingPlansProps {
  open: boolean;
  onClose: () => void;
}

export function PricingPlans({ open, onClose }: PricingPlansProps) {
  const plans = [
    {
      name: "Starter",
      description: "Everything you need to get started.",
      price: "$19",
      per: "per month",
      features: [
        { text: "Custom domains", active: true },
        { text: "Edge content delivery", active: true },
        { text: "Advanced analytics", active: true },
        { text: "Quarterly workshops", active: false },
        { text: "Single sign-on (SSO)", active: false },
        { text: "Priority phone support", active: false },
      ],
    },
    {
      name: "Growth",
      description: "All the extras for your growing team.",
      price: "$49",
      per: "per month",
      features: [
        { text: "Custom domains", active: true },
        { text: "Edge content delivery", active: true },
        { text: "Advanced analytics", active: true },
        { text: "Quarterly workshops", active: true },
        { text: "Single sign-on (SSO)", active: false },
        { text: "Priority phone support", active: false },
      ],
    },
    {
      name: "Scale",
      description: "Added flexibility at scale.",
      price: "$99",
      per: "per month",
      features: [
        { text: "Custom domains", active: true },
        { text: "Edge content delivery", active: true },
        { text: "Advanced analytics", active: true },
        { text: "Quarterly workshops", active: true },
        { text: "Single sign-on (SSO)", active: true },
        { text: "Priority phone support", active: true },
      ],
    },
  ];

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="pricingModal">
        <DialogHeader>
          <DialogTitle className="pricingModal__title">Upgrade your plan</DialogTitle>
          <p className="pricingModal__subtitle">
            Choose the plan that fits your needs and unlock premium features.
          </p>
        </DialogHeader>

        <div className="pricingModal__cards">
          {plans.map((plan) => (
            <Card key={plan.name} className="pricingCard">
              <CardHeader>
                <CardTitle className="pricingCard__name">{plan.name}</CardTitle>
                <p className="pricingCard__desc">{plan.description}</p>
              </CardHeader>

              <CardContent>
                <div className="pricingCard__price">
                  {plan.price}
                  <span className="pricingCard__usd">USD</span>
                </div>
                <p className="pricingCard__per">{plan.per}</p>

                <Button className="pricingCard__btn">Start a free trial</Button>

                <div>
                  <p className="pricingCard__section">Start selling with:</p>
                  <ul className="pricingCard__features">
                    {plan.features.map((feature, i) => (
                      <li
                        key={i}
                        className={`pricingCard__feature ${
                          feature.active ? "active" : "inactive"
                        }`}
                      >
                        ＋ {feature.text}
                      </li>
                    ))}
                  </ul>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </DialogContent>
    </Dialog>
  );
}
